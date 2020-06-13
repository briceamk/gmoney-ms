package cm.g2s.uaa.service.broker.publisher;

import cm.g2s.uaa.service.broker.payload.CreateAccountRequest;
import cm.g2s.uaa.service.broker.payload.CreatePartnerRequest;
import cm.g2s.uaa.service.broker.payload.RemovePartnerRequest;

public interface UaaEventPublisherService {
    void onCreatePartnerEvent(CreatePartnerRequest createPartnerRequest);

    void onCreateAccountEvent(CreateAccountRequest accountRequest);

    void onRemovePartnerEvent(RemovePartnerRequest removePartnerRequest);
}
