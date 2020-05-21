package cm.g2s.loan.service.broker.consumer;

import cm.g2s.loan.service.broker.payload.DebitAccountResponse;

public interface LoanEventConsumerService {
    void observeDebitAccountResponse(DebitAccountResponse response);
}
