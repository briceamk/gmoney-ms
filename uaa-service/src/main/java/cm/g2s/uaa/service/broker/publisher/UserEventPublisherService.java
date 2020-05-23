package cm.g2s.uaa.service.broker.publisher;

import cm.g2s.uaa.service.broker.payload.RemovePartnerRequest;
import cm.g2s.uaa.service.broker.payload.CreateAccountRequest;
import cm.g2s.uaa.service.broker.payload.CreatePartnerRequest;

public interface UserEventPublisherService {
    void onCreatePartnerEvent(CreatePartnerRequest createPartnerRequest);

    void onCreateAccountEvent(CreateAccountRequest accountRequest);

    void onRemovePartnerEvent(RemovePartnerRequest removePartnerRequest);
}
