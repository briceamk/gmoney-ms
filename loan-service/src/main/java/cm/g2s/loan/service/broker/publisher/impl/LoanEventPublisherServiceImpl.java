package cm.g2s.loan.service.broker.publisher.impl;

import cm.g2s.loan.infrastructure.broker.LoanEventSource;
import cm.g2s.loan.service.broker.payload.CreateTransactionRequest;
import cm.g2s.loan.service.broker.publisher.LoanEventPublisherService;
import cm.g2s.loan.service.broker.payload.DebitAccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service("loanEventPublisherService")
public class LoanEventPublisherServiceImpl implements LoanEventPublisherService {

    private final LoanEventSource eventSource;


    @Override
    @TransactionalEventListener
    public void onTransactionCreatedEvent(@Payload CreateTransactionRequest transactionRequest) {
        log.info("Publishing Create Transaction action to rabbitmq");
        eventSource.transactionCreated().send(MessageBuilder.withPayload(transactionRequest).build());
    }

    @Override
    @TransactionalEventListener
    public void onDebitAccountEvent(@Payload DebitAccountRequest debitAccountRequest) {
        log.info("Publishing Debit Account action to rabbitmq");
        eventSource.accountDebited().send(MessageBuilder.withPayload(debitAccountRequest).build());
    }
}
