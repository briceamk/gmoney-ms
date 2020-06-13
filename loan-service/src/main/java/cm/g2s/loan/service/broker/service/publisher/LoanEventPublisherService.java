package cm.g2s.loan.service.broker.service.publisher;

import cm.g2s.loan.service.broker.payload.ConfirmDebitAccountRequest;
import cm.g2s.loan.service.broker.payload.CreateTransactionRequest;
import cm.g2s.loan.service.broker.payload.DebitAccountRequest;

public interface LoanEventPublisherService {
    void onTransactionCreatedEvent(CreateTransactionRequest transactionRequest);

    void onDebitAccountEvent(DebitAccountRequest debitAccountRequest);

    void onConfirmDebitAccountEvent(ConfirmDebitAccountRequest confirmDebitAccountRequest);
}
