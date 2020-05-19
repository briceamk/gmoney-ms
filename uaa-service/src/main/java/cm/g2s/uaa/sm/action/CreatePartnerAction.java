package cm.g2s.uaa.sm.action;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.event.UserEvent;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.service.UserService;
import cm.g2s.uaa.service.broker.publisher.UserEventPublisherService;
import cm.g2s.uaa.shared.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreatePartnerAction implements Action<UserState, UserEvent> {

    private final UserService userService;
    private final UserEventPublisherService publisherService;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        String userId = (String) context.getMessage().getHeaders().get(UaaConstantType.USER_ID_HEADER);
        UserDto userDto = userService.findById(userId);

        log.info("Send Partner creation request to the queue for userId: userId");
        publisherService.onCreatePartnerEvent(userDto);
    }
}
