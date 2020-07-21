package cm.g2s.loan.service.broker.service.publisher.impl;

import cm.g2s.loan.constant.LoanConstantType;
import cm.g2s.loan.infrastructure.broker.LoanEventSource;
import cm.g2s.loan.service.broker.payload.ConfirmDebitAccountRequest;
import cm.g2s.loan.service.broker.payload.CreateTransactionRequest;
import cm.g2s.loan.service.broker.payload.DebitAccountRequest;
import cm.g2s.loan.service.broker.payload.CreateSendMoneySuccessEmailRequest;
import cm.g2s.loan.service.broker.service.publisher.LoanEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public void onTransactionCreatedEvent(CreateTransactionRequest createTransactionRequest) {
        log.info("Publishing Create Transaction action to rabbitmq");
        eventSource.loanChannel().send(
                MessageBuilder
                        .withPayload(createTransactionRequest)
                        .setHeader(LoanConstantType.ROUTING_KEY_EXPRESSION, LoanConstantType.ROUTING_KEY_CREATE_TRANSACTION)
                        .build());
    }

    @Override
    @TransactionalEventListener
    public void onDebitAccountEvent(DebitAccountRequest debitAccountRequest) {
        log.info("Publishing Debit Account action to rabbitmq");
        eventSource.loanChannel().send(
                MessageBuilder
                        .withPayload(debitAccountRequest)
                        .setHeader(LoanConstantType.ROUTING_KEY_EXPRESSION, LoanConstantType.ROUTING_KEY_DEBIT_ACCOUNT)
                        .build());
    }

    @Override
    @TransactionalEventListener
    public void onConfirmDebitAccountEvent(ConfirmDebitAccountRequest confirmDebitAccountRequest) {
        log.info("Publishing Confirm Debit Account action to rabbitmq");
        eventSource.loanChannel().send(MessageBuilder
                .withPayload(confirmDebitAccountRequest)
                .setHeader(LoanConstantType.ROUTING_KEY_EXPRESSION, LoanConstantType.ROUTING_KEY_CONFIRM_DEBIT_ACCOUNT)
                .build());
    }

    @Override
    @TransactionalEventListener
    public void onCreateSendMoneySuccessEmailEvent(CreateSendMoneySuccessEmailRequest createSendMoneySuccessEmailRequest) {
        log.info("Publishing Send Money Success Email action to rabbitmq {}", createSendMoneySuccessEmailRequest.toString());
        eventSource.loanChannel().send(MessageBuilder
                .withPayload(createSendMoneySuccessEmailRequest)
                .setHeader(LoanConstantType.ROUTING_KEY_EXPRESSION, LoanConstantType.ROUTING_KEY_CREATE_SEND_MONEY_SUCCESS_EMAIL)
                .build());

    }
}
