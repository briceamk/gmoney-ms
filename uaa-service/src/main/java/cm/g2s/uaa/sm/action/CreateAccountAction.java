package cm.g2s.uaa.sm.action;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.event.UserEvent;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.service.broker.payload.CreateAccountRequest;
import cm.g2s.uaa.service.broker.publisher.UaaEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateAccountAction implements Action<UserState, UserEvent> {

    private final UaaEventPublisherService publisherService;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        String userId = (String) context.getMessage().getHeaders().getOrDefault(UaaConstantType.USER_ID_HEADER, "");
        String partnerId = (String) context.getMessage().getHeaders().getOrDefault(UaaConstantType.PARTNER_ID_HEADER, "");
        if(!userId.isEmpty() && !partnerId.isEmpty()) {
            CreateAccountRequest accountRequest = CreateAccountRequest.builder()
                    .userId(userId)
                    .partnerId(partnerId)
                    .build();
            log.info("Send Account creation request to the queue for userId: userId");
            publisherService.onCreateAccountEvent(accountRequest);
        }

    }
}
