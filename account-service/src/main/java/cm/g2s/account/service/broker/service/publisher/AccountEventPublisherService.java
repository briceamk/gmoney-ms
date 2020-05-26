package cm.g2s.account.service.broker.service.publisher;


import cm.g2s.account.service.broker.payload.ConfirmDebitAccountResponse;
import cm.g2s.account.service.broker.payload.CreateAccountResponse;
import cm.g2s.account.service.broker.payload.DebitAccountResponse;

public interface AccountEventPublisherService {

    void onCreateAccountResponseEvent(CreateAccountResponse response);

    void onDebitAccountResponseEvent(DebitAccountResponse response);

    void onConfirmDebitAccountResponseEvent(ConfirmDebitAccountResponse response);
}
