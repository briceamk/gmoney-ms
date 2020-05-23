package cm.g2s.account.service.broker.service.consumer;



import cm.g2s.account.service.broker.payload.CreateAccountRequest;
import cm.g2s.account.service.broker.payload.DebitAccountRequest;

public interface AccountEventConsumerService {
    void observeAccountCreateRequest(CreateAccountRequest accountRequest);
    void observeAccountDebitRequest( DebitAccountRequest debitAccountRequest);
}
