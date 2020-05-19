package cm.g2s.uaa.sm;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.event.UserEvent;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.service.UserService;
import cm.g2s.uaa.shared.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStateChangeInterceptor extends StateMachineInterceptorAdapter<UserState, UserEvent> {

    private final UserService userService;

    @Override
    public void preStateChange(State<UserState, UserEvent> state, Message<UserEvent> message, Transition<UserState, UserEvent> transition, StateMachine<UserState, UserEvent> stateMachine) {
        log.info("User State change ....");
        Optional.ofNullable(message)
                .flatMap(userEventMessage -> Optional.ofNullable((String) userEventMessage.getHeaders().getOrDefault(UaaConstantType.USER_ID_HEADER, " ")))
                .ifPresent(userId -> {
                    log.info("Saving state for userId: {} Status: {}", userId, state.getId());

                    UserDto userDto = userService.findById(userId);

                    log.info("Saving for userDto: {} ", userDto);
                    userDto.setState(state.getId().name());
                    userService.update(null, userDto);
                });
    }
}
