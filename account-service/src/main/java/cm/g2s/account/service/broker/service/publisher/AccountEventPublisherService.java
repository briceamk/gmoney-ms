package cm.g2s.account.service.broker.service.publisher;


import cm.g2s.account.service.broker.payload.CreateAccountResponse;

public interface AccountEventPublisherService {
    void onCreateAccountResponseEvent(CreateAccountResponse response);
}
