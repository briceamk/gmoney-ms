package cm.g2s.transaction.service.broker.service.publisher.impl;

import cm.g2s.transaction.constant.TransactionConstantType;
import cm.g2s.transaction.infrastructure.broker.TransactionEventSource;
import cm.g2s.transaction.service.broker.payload.CreateTransactionResponse;
import cm.g2s.transaction.service.broker.payload.SendMoneyResponse;
import cm.g2s.transaction.service.broker.service.publisher.TransactionEventPublisherService;
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
    public void onCreateTransactionResponseEvent(CreateTransactionResponse createTransactionResponse) {
        log.info("Sending creation transaction response to loan-service");
        eventSource.transactionChannel().send(
                MessageBuilder
                        .withPayload(createTransactionResponse)
                        .setHeader(TransactionConstantType.ROUTING_KEY_EXPRESSION, TransactionConstantType.ROUTING_KEY_CREATE_TRANSACTION_RESPONSE)
                        .build()
        );
    }

    @Override
    @TransactionalEventListener
    public void onSendMoneyResponseEvent(SendMoneyResponse sendMoneyResponse) {
        log.info("Sending send money response to loan-service");
        eventSource.transactionChannel().send(
                MessageBuilder
                        .withPayload(sendMoneyResponse)
                        .setHeader(TransactionConstantType.ROUTING_KEY_EXPRESSION, TransactionConstantType.ROUTING_KEY_SEND_MONEY_RESPONSE)
                        .build()
        );
    }
}
