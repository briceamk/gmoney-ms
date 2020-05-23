package cm.g2s.uaa.sm.action;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.domain.event.UserEvent;
import cm.g2s.uaa.domain.model.User;
import cm.g2s.uaa.domain.model.UserState;
import cm.g2s.uaa.service.UserService;
import cm.g2s.uaa.service.broker.payload.CreatePartnerRequest;
import cm.g2s.uaa.service.broker.publisher.UserEventPublisherService;
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
        String userId = (String) context.getMessage().getHeaders().getOrDefault(UaaConstantType.USER_ID_HEADER, "");
        User user = userService.findById(userId);
        if(user != null) {
            CreatePartnerRequest createPartnerRequest = transform(user);
            log.info("Send Partner creation request to the queue for userId: {}", userId);
            publisherService.onCreatePartnerEvent(createPartnerRequest);
        }

    }

    private CreatePartnerRequest transform(User user) {
        return CreatePartnerRequest.builder()
                .firstName(user.getFirstName() != null? user.getFirstName() :  "")
                .lastName(user.getLastName())
                .email(user.getEmail())
                .city(user.getCity())
                .companyId(user.getCompanyId() != null? user.getCompanyId(): "")
                .mobile(user.getMobile())
                .userId(user.getId())
                .build();
    }
}
