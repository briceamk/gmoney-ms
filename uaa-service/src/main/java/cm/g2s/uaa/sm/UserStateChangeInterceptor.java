package cm.g2s.uaa.sm;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.event.UserEvent;
import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserStateChangeInterceptor extends StateMachineInterceptorAdapter<UserState, UserEvent> {

    private final UserService userService;

    @Override
    public void preStateChange(State<UserState, UserEvent> state, Message<UserEvent> message,
                               Transition<UserState, UserEvent> transition, StateMachine<UserState, UserEvent> stateMachine) {
        log.info("User State change ....");

        String userId = (String) message.getHeaders().getOrDefault(UaaConstantType.USER_ID_HEADER, "");
        String partnerId = (String) message.getHeaders().getOrDefault(UaaConstantType.PARTNER_ID_HEADER, "");
        if(!userId.isEmpty()) {
            log.info("Saving state for userId: {} Status: {}", userId, state.getId());
            User user = userService.findById(userId);
            user.setState(state.getId());
            if(!partnerId.isEmpty()) {
                log.info("We set partnerId with id: {}", partnerId);
                user.setPartnerId(partnerId);
            }

            userService.update(user);
        }

    }
}
