package cm.g2s.transaction.service.broker.consumer;

import cm.g2s.transaction.service.broker.payload.CreateTransactionRequest;
import cm.g2s.transaction.service.broker.payload.JobRequest;

public interface TransactionEventConsumerService {
    void observeCreateTransactionRequest(CreateTransactionRequest transactionRequest);
    void observeSendMoneyRequest(JobRequest jobRequest);
}
