package cm.g2s.uaa.service.broker.consumer;

import cm.g2s.uaa.service.broker.payload.CreateAccountResponse;
import cm.g2s.uaa.service.broker.payload.CreatePartnerResponse;

public interface UaaEventConsumerService {
    void observeCreatePartnerResponse(CreatePartnerResponse response);
    void observeCreateAccountResponse(CreateAccountResponse response);
}
