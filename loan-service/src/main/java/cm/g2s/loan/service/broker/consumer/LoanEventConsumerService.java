package cm.g2s.loan.service.broker.consumer;

import cm.g2s.loan.service.broker.payload.ConfirmDebitAccountResponse;
import cm.g2s.loan.service.broker.payload.CreateTransactionResponse;
import cm.g2s.loan.service.broker.payload.DebitAccountResponse;
import cm.g2s.loan.service.broker.payload.SendMoneyResponse;

public interface LoanEventConsumerService {

    void observeDebitAccountResponse(DebitAccountResponse debitAccountResponse);

    void observeTransactionCreateResponse(CreateTransactionResponse createTransactionResponse);

    void observeSendMoneyResponse(SendMoneyResponse sendMoneyResponse);

    void observeConfirmAccountDebitResponse(ConfirmDebitAccountResponse confirmDebitAccountResponse);
}
