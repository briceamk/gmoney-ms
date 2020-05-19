package cm.g2s.uaa.service.broker.consumer;

import cm.g2s.uaa.service.broker.consumer.payload.CreateAccountResponse;
import cm.g2s.uaa.service.broker.consumer.payload.CreatePartnerResponse;

public interface UserEventConsumerService {
    void observeCreatePartnerResponse(CreatePartnerResponse response);
    void observeCreateAccountResponse(CreateAccountResponse response);
}
