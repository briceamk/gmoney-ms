package cm.g2s.transaction.service.broker.publisher.impl;

import cm.g2s.transaction.infrastructure.broker.TransactionEventSource;
import cm.g2s.transaction.service.broker.payload.CreateTransactionResponse;
import cm.g2s.transaction.service.broker.payload.SendMoneyResponse;
import cm.g2s.transaction.service.broker.publisher.TransactionEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service("transactionEventPublisherService")
public class TransactionEventPublisherServiceImpl implements TransactionEventPublisherService {

    private final TransactionEventSource eventSource;

    @Override
    @TransactionalEventListener
    public void onCreateTransactionResponseEvent(CreateTransactionResponse response) {
        log.info("Sending creation transaction response to loan-service");
        eventSource.transactionCreatedResponse().send(MessageBuilder.withPayload(response).build());
    }

    @Override
    @TransactionalEventListener
    public void onSendMoneyResponseEvent(SendMoneyResponse response) {
        log.info("Sending send money response to loan-service");
        eventSource.sendMoneyResponse().send(MessageBuilder.withPayload(response).build());
    }
}
