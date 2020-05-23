package cm.g2s.transaction.service.broker.publisher;

import cm.g2s.transaction.service.broker.payload.CreateTransactionResponse;

public interface TransactionEventPublisherService {
    void onCreateTransactionResponseEvent(CreateTransactionResponse response);
}
