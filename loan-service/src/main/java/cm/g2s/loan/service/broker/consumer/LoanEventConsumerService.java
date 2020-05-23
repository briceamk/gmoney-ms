package cm.g2s.loan.service.broker.consumer;

import cm.g2s.loan.service.broker.payload.CreateTransactionResponse;
import cm.g2s.loan.service.broker.payload.DebitAccountResponse;

public interface LoanEventConsumerService {
    void observeDebitAccountResponse(DebitAccountResponse response);
    void observeTransactionCreateResponse(CreateTransactionResponse response);
}
