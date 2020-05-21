package cm.g2s.loan.service.broker.publisher.impl;

import cm.g2s.loan.infrastructure.broker.LoanEventSource;
import cm.g2s.loan.service.broker.publisher.LoanEventPublisherService;
import cm.g2s.loan.service.broker.payload.DebitAccount;
import cm.g2s.loan.shared.dto.LoanDto;
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
    public void onSendMoneyEvent(LoanDto loanDto) {
        log.info("Publishing Loan validate action to rabbitmq");
        eventSource.moneySent().send(MessageBuilder.withPayload(loanDto).build());
    }

    @Override
    @TransactionalEventListener
    public void onDebitAccountEvent(DebitAccount debitAccount) {
        log.info("Publishing Debit Account action to rabbitmq");
        eventSource.accountDebited().send(MessageBuilder.withPayload(debitAccount).build());
    }
}
