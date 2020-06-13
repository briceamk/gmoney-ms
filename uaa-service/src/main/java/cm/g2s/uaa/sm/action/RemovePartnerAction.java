package cm.g2s.uaa.sm.action;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.event.UserEvent;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.service.broker.payload.RemovePartnerRequest;
import cm.g2s.uaa.service.broker.publisher.UaaEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemovePartnerAction implements Action<UserState, UserEvent> {

    private final UaaEventPublisherService publisherService;

    @Override
    public void execute(StateContext<UserState, UserEvent> context) {
        String userId = (String) context.getMessage().getHeaders().getOrDefault(UaaConstantType.USER_ID_HEADER, "");
        if(!userId.isEmpty()) {
            RemovePartnerRequest removePartnerRequest = RemovePartnerRequest.builder()
                    .userId(userId)
                    .build();
            log.info("Send Account creation failed request to the queue for userId: userId");
            publisherService.onRemovePartnerEvent(removePartnerRequest);
        }


    }
}
