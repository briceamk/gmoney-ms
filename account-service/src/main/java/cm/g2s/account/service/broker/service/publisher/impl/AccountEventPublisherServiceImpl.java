package cm.g2s.account.service.broker.service.publisher.impl;

import cm.g2s.account.infrastructure.broker.AccountEventSource;
import cm.g2s.account.service.broker.payload.ConfirmDebitAccountResponse;
import cm.g2s.account.service.broker.payload.CreateAccountResponse;
import cm.g2s.account.service.broker.payload.DebitAccountResponse;
import cm.g2s.account.service.broker.service.publisher.AccountEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service("accountEventPublisherService")
public class AccountEventPublisherServiceImpl implements AccountEventPublisherService {

    private final AccountEventSource eventSource;

    @Override
    @TransactionalEventListener
    public void onCreateAccountResponseEvent(CreateAccountResponse response) {
        eventSource.accountCreatedResponse().send(MessageBuilder.withPayload(response).build());

    }

    @Override
    @TransactionalEventListener
    public void onDebitAccountResponseEvent(DebitAccountResponse response) {
        log.info("Sending debit account response to loan-service");
        eventSource.accountDebitedResponse().send(MessageBuilder.withPayload(response).build());
    }

    @Override
    @TransactionalEventListener
    public void onConfirmDebitAccountResponseEvent(ConfirmDebitAccountResponse response) {
        log.info("Sending confirm debit account response to loan-service");
        eventSource.confirmAccountDebitResponse().send(MessageBuilder.withPayload(response).build());
    }
}
