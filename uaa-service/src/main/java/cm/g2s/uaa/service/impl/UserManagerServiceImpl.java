package cm.g2s.uaa.service.impl;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.event.UserEvent;
import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.infrastructure.repository.UserRepository;
import cm.g2s.uaa.service.RoleService;
import cm.g2s.uaa.service.UserManagerService;
import cm.g2s.uaa.service.broker.consumer.dto.PartnerDto;
import cm.g2s.uaa.service.broker.consumer.payload.CreatePartnerResponse;
import cm.g2s.uaa.shared.dto.RoleDto;
import cm.g2s.uaa.shared.dto.UserDto;
import cm.g2s.uaa.shared.exception.BadRequestException;
import cm.g2s.uaa.shared.mapper.RoleMapper;
import cm.g2s.uaa.shared.mapper.UserMapper;
import cm.g2s.uaa.sm.UserStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service("userManagerService")
public class UserManagerServiceImpl implements UserManagerService {

    private final StateMachineFactory<UserState, UserEvent> machineFactory;

    private final UserRepository userRepository;

    private final UserStateChangeInterceptor userStateChangeInterceptor;

    private final RoleMapper roleMapper;
    private final UserMapper userMapper;

    private final RoleService roleService;

    @Override
    @Transactional
    public UserDto createNewUser(User user) {
        user = userRepository.save(user);
        RoleDto roleDto = roleService.findByName(UaaConstantType.DEFAULT_USER_ROLE);
        user.setRoles(new LinkedHashSet<>(Arrays.asList(roleMapper.map(roleDto))));
        UserDto savedUserDto = userMapper.map(userRepository.save(user));
        //publish broker USER_CREATED_PENDING to partner-service
        sendUserEvent(savedUserDto, UserEvent.CREATE_PARTNER, null);
        return savedUserDto;
    }

    @Override
    public void processPartnerCreationResponse(String userId, Boolean creationPartnerError, CreatePartnerResponse response) {
        log.info("Process partner creation action result");
        Optional<User> optionalUser = userRepository.findById(userId);

        optionalUser.ifPresent(user -> {
            if(!creationPartnerError) {
                // Change state and update partnerId in User Object
                sendUserEvent(userMapper.map(user), UserEvent.CREATE_PARTNER_PASSED, response.getPartnerDto());
                // Wait for status change
                awaitForStatus(userId, UserState.PARTNER_CREATED);

                UserDto userDtoCreated = userMapper.map(userRepository.findById(userId).get());

                sendUserEvent(userDtoCreated, UserEvent.CREATE_ACCOUNT, response.getPartnerDto());

            } else {
                sendUserEvent(userMapper.map(user), UserEvent.CREATE_PARTNER_FAILED, null);

                // Wait for status change
                awaitForStatus(userId, UserState.PARTNER_CREATED_EXCEPTION);

                //We remove user because of failing when creating his partner account

                User userToRemove = userRepository.findById(userId).orElseThrow(
                        () -> {
                            log.error("user with id {} not found", userId);
                            throw new BadRequestException(String.format("User with id %s not found", userId));
                        }
                );

                userRepository.delete(userToRemove);

            }
        });
    }

    @Override
    public void processAccountCreationResponse(String userId, Boolean creationAccountError) {
        log.info("Process account creation action result");
        Optional<User> optionalUser = userRepository.findById(userId);

        optionalUser.ifPresent(user -> {
            if(!creationAccountError) {
                // Change state and update partnerId in User Object
                sendUserEvent(userMapper.map(user), UserEvent.CREATE_ACCOUNT_PASSED, null);
                // Wait for status change
                awaitForStatus(userId, UserState.ACCOUNT_CREATED);

                UserDto userDtoCreated = userMapper.map(userRepository.findById(userId).get());

                sendUserEvent(userDtoCreated, UserEvent.CREATE_USER, null);

            } else {
                sendUserEvent(userMapper.map(user), UserEvent.CREATED_ACCOUNT_FAILED, null);

                // Wait for status change
                awaitForStatus(userId, UserState.ACCOUNT_CREATED_EXCEPTION);

                //We remove user account because of failing when creating his partner account

                User userToRemove = userRepository.findById(userId).orElseThrow(
                        () -> {
                            log.error("user with id {} not found", userId);
                            throw new BadRequestException(String.format("User with id %s not found", userId));
                        }
                );

                userRepository.delete(userToRemove);

            }
        });
    }

    private void awaitForStatus(String userId, UserState userState) {
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicInteger loopCount = new AtomicInteger(0);

        while(!found.get()) {
            if(loopCount.incrementAndGet()>10) {
                found.set(true);
                log.debug("Loop retries exceeded!");
            }

            userRepository.findById(userId).ifPresent(user -> {
                if(user.getState().equals(userState)) {
                    found.set(true);
                    log.debug("User found");
                } else {
                    log.debug("User state not equals. Expected: {}, Found: {}", userState, user.getState().name());
                }
            });

            if (!found.get()) {
                try {
                    log.debug("Sleeping for retry");
                    Thread.sleep(100);
                } catch (Exception e) {
                    // do nothing
                }
            }
        }
    }

    private void sendUserEvent(UserDto userDto, UserEvent userEvent, Object payload) {
        StateMachine<UserState, UserEvent> machine = build(userDto);
        PartnerDto partnerDto = null;

        if(payload != null && payload instanceof PartnerDto)
            partnerDto = (PartnerDto) payload;

        MessageBuilder messageBuilder = MessageBuilder.withPayload(userEvent)
                .setHeader(UaaConstantType.USER_ID_HEADER, userDto.getId());

        if(partnerDto != null) {
            messageBuilder.setHeader(UaaConstantType.PARTNER_ID_HEADER, partnerDto.getId());
        }


        machine.sendEvent(messageBuilder.build());
    }

    private StateMachine<UserState, UserEvent> build(UserDto userDto) {
        StateMachine<UserState, UserEvent> machine = machineFactory.getStateMachine(userDto.getId());

        machine.stop();

        machine.getStateMachineAccessor()
                .doWithAllRegions(machineAccess -> {
                    machineAccess.addStateMachineInterceptor(userStateChangeInterceptor);
                    machineAccess.resetStateMachine(new DefaultStateMachineContext<>(UserState.valueOf(userDto.getState()), null, null, null));
                });

        machine.start();

        return machine;

    }
}
