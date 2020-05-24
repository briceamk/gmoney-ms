package cm.g2s.uaa.service.impl;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.event.UserEvent;
import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.service.UserManagerService;
import cm.g2s.uaa.service.UserService;
import cm.g2s.uaa.service.broker.payload.CreatePartnerResponse;
import cm.g2s.uaa.web.payload.SignUp;
import cm.g2s.uaa.sm.UserStateChangeInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service("userManagerService")
public class UserManagerServiceImpl implements UserManagerService {

    private final StateMachineFactory<UserState, UserEvent> machineFactory;

    private final UserService userService;

    private final AuthServiceImpl authService;

    private final UserStateChangeInterceptor userStateChangeInterceptor;



    @Override
    @Transactional
    public User createNewUser(User user, SignUp signUp) {
        if(user == null)
            user = authService.signUp(signUp);
        else
            user = userService.create(user);

        //prevent partner-service with event CREATE_PARTNER
        sendUserEvent(user, UserEvent.CREATE_PARTNER, null);
        return user;
    }

    @Override
    @Transactional
    public void processPartnerCreationResponse(String userId, Boolean creationPartnerError, CreatePartnerResponse response) {
        log.info("Process partner creation action result");
        User user = userService.findById(userId);

        if(user != null) {
            if(!creationPartnerError) {
                // Change state and update partnerId in User Object
                sendUserEvent(user, UserEvent.CREATE_PARTNER_PASSED, response);
                // Wait for status change
                awaitForStatus(userId, UserState.PARTNER_CREATED);

                User savedUser = userService.findById(userId);

                sendUserEvent(savedUser, UserEvent.CREATE_ACCOUNT, response);

            } else {
                sendUserEvent(user, UserEvent.CREATE_PARTNER_FAILED, response);

                // Wait for status change
                awaitForStatus(userId, UserState.PARTNER_CREATED_EXCEPTION);

                //We remove user because of failing when creating his partner account

                userService.deleteById(userId);

            }
        } else {
            log.error("User with id {} not found", userId);
        }
    }

    @Override
    @Transactional
    public void processAccountCreationResponse(String userId, Boolean creationAccountError) {
        log.info("Process account creation action result");
        User user = userService.findById(userId);

        if(user != null) {
            if(!creationAccountError) {
                // Change state and update partnerId in User Object
                sendUserEvent(user, UserEvent.CREATE_ACCOUNT_PASSED, null);
                // Wait for status change
                awaitForStatus(userId, UserState.ACCOUNT_CREATED);

                User userCreated = userService.findById(userId);

                sendUserEvent(userCreated, UserEvent.CREATE_USER, null);

            } else {
                sendUserEvent(user, UserEvent.CREATED_ACCOUNT_FAILED, null);

                // Wait for status change
                awaitForStatus(userId, UserState.ACCOUNT_CREATED_EXCEPTION);

                //We remove user account because of failing when creating his partner account

                userService.deleteById(userId);

            }
        }else {
            log.error("User with id {} not found", userId);
        }
    }

    private void awaitForStatus(String userId, UserState userState) {
        AtomicBoolean found = new AtomicBoolean(false);
        AtomicInteger loopCount = new AtomicInteger(0);

        while(!found.get()) {
            if(loopCount.incrementAndGet()>10) {
                found.set(true);
                log.debug("Loop retries exceeded!");
            }

            User user = userService.findById(userId);
            if(user != null) {
                if(user.getState().equals(userState)) {
                    found.set(true);
                    log.debug("User found");
                } else {
                    log.debug("User state not equals. Expected: {}, Found: {}", userState, user.getState().name());
                }
            }

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

    private void sendUserEvent(User user, UserEvent userEvent, Object payload) {
        StateMachine<UserState, UserEvent> machine = build(user);
        CreatePartnerResponse partnerResponse = null;

        if(payload instanceof CreatePartnerResponse)
            partnerResponse = (CreatePartnerResponse) payload;

        MessageBuilder messageBuilder = MessageBuilder.withPayload(userEvent)
                .setHeader(UaaConstantType.USER_ID_HEADER, user.getId());

        if(partnerResponse != null) {
            messageBuilder.setHeader(UaaConstantType.PARTNER_ID_HEADER, partnerResponse.getPartnerId());
        }


        machine.sendEvent(messageBuilder.build());
    }

    private StateMachine<UserState, UserEvent> build(User user) {
        StateMachine<UserState, UserEvent> machine = machineFactory.getStateMachine(user.getId());

        machine.stop();

        machine.getStateMachineAccessor()
                .doWithAllRegions(machineAccess -> {
                    machineAccess.addStateMachineInterceptor(userStateChangeInterceptor);
                    machineAccess.resetStateMachine(new DefaultStateMachineContext<>(user.getState(), null, null, null));
                });

        machine.start();

        return machine;

    }
}
