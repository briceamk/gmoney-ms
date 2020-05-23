package cm.g2s.partner.service.broker.service.consumer;

import cm.g2s.partner.service.broker.payload.CreatePartnerRequest;
import cm.g2s.partner.service.broker.payload.RemovePartnerRequest;

public interface PartnerEventConsumerService {
    void observePartnerCreateRequest(CreatePartnerRequest createPartnerRequest);
    void observePartnerRemoveRequest(RemovePartnerRequest removePartnerRequest);
}
