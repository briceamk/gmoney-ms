package cm.g2s.loan.service.broker.service.consumer;

import cm.g2s.loan.service.broker.payload.*;

public interface LoanEventConsumerService {

    void observeDebitAccountResponse(DebitAccountResponse debitAccountResponse);

    void observeTransactionCreateResponse(CreateTransactionResponse createTransactionResponse);

    void observeSendMoneyResponse(SendMoneyResponse sendMoneyResponse);

    void observeConfirmAccountDebitResponse(ConfirmDebitAccountResponse confirmDebitAccountResponse);

    void observeCreateSendMoneySuccessEmailResponse(CreateSendMoneySuccessEmailResponse createSendMoneySuccessEmailResponse);
}
