package cm.g2s.partner.service.broker.service.producer;

import cm.g2s.partner.service.broker.payload.CreatePartnerResponse;

public interface PartnerEventPublisherService {
    void onCreatePartnerResponseEvent(CreatePartnerResponse response);
}
