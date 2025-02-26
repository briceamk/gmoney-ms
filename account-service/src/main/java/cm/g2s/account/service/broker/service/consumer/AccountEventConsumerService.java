package cm.g2s.account.service.broker.service.consumer;



import cm.g2s.account.service.broker.payload.ConfirmDebitAccountRequest;
import cm.g2s.account.service.broker.payload.CreateAccountRequest;
import cm.g2s.account.service.broker.payload.DebitAccountRequest;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public interface AccountEventConsumerService {

    void observeAccountCreateRequest(CreateAccountRequest accountRequest);

    void observeAccountDebitRequest(DebitAccountRequest debitAccountRequest);

    void observeConfirmAccountDebitRequest(ConfirmDebitAccountRequest confirmDebitAccountRequest);
}
