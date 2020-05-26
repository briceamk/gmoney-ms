package cm.g2s.transaction.service.broker.publisher;

import cm.g2s.transaction.service.broker.payload.CreateTransactionResponse;
import cm.g2s.transaction.service.broker.payload.SendMoneyResponse;

public interface TransactionEventPublisherService {
    void onCreateTransactionResponseEvent(CreateTransactionResponse response);
    void onSendMoneyResponseEvent(SendMoneyResponse response);
}
