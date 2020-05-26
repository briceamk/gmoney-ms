package cm.g2s.transaction.service.broker.consumer;

import cm.g2s.transaction.service.broker.payload.CreateTransactionRequest;
import cm.g2s.transaction.service.broker.payload.SendMoneyRequest;
import cm.g2s.transaction.service.loan.dto.LoanDto;

public interface TransactionEventConsumerService {
    void observeCreateTransactionRequest(CreateTransactionRequest transactionRequest);
    void observeSendMoneyRequest(SendMoneyRequest sendMoneyRequest);
}
