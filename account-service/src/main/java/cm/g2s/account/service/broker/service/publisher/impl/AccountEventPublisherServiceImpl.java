package cm.g2s.account.service.broker.service.publisher.impl;

import cm.g2s.account.constant.AccountConstantType;
import cm.g2s.account.infrastructure.broker.AccountEventSource;
import cm.g2s.account.service.broker.payload.ConfirmDebitAccountResponse;
import cm.g2s.account.service.broker.payload.CreateAccountResponse;
import cm.g2s.account.service.broker.payload.DebitAccountResponse;
import cm.g2s.account.service.broker.service.publisher.AccountEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service("accountEventPublisherService")
public class AccountEventPublisherServiceImpl implements AccountEventPublisherService {

    private final AccountEventSource eventSource;

    @Override
    @TransactionalEventListener
    public void onCreateAccountResponseEvent(CreateAccountResponse createAccountResponse) {
        log.info("Sending create account response to uaa-service");
        eventSource.accountChannel().send(
                MessageBuilder
                        .withPayload(createAccountResponse)
                        .setHeader(AccountConstantType.ROUTING_KEY_EXPRESSION, AccountConstantType.ROUTING_KEY_CREATE_ACCOUNT_RESPONSE)
                        .build()
        );

    }

    @Override
    @TransactionalEventListener
    public void onDebitAccountResponseEvent(DebitAccountResponse debitAccountResponse) {
        log.info("Sending debit account response to loan-service");
        eventSource.accountChannel().send(
                MessageBuilder
                        .withPayload(debitAccountResponse)
                        .setHeader(AccountConstantType.ROUTING_KEY_EXPRESSION, AccountConstantType.ROUTING_KEY_DEBIT_ACCOUNT_RESPONSE)
                        .build()
        );
    }

    @Override
    @TransactionalEventListener
    public void onConfirmDebitAccountResponseEvent(ConfirmDebitAccountResponse confirmDebitAccountResponse) {
        log.info("Sending confirm debit account response to loan-service");
        eventSource.accountChannel().send(
                MessageBuilder
                        .withPayload(confirmDebitAccountResponse)
                        .setHeader(AccountConstantType.ROUTING_KEY_EXPRESSION, AccountConstantType.ROUTING_KEY_CONFIRM_DEBIT_ACCOUNT_RESPONSE)
                        .build());
    }
}
