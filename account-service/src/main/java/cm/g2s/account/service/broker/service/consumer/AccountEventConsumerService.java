package cm.g2s.account.service.broker.service.consumer;


import cm.g2s.account.service.broker.payload.DebitAccount;
import cm.g2s.account.service.user.model.UserDto;

public interface AccountEventConsumerService {
    void observeAccountCreateRequest(UserDto userDto);
    void observeAccountDebitRequest(DebitAccount debitAccount);
}
