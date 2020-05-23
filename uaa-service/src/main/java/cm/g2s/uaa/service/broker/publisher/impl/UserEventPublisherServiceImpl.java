package cm.g2s.uaa.service.broker.publisher.impl;

import cm.g2s.uaa.infrastructure.broker.UserEventSource;
import cm.g2s.uaa.service.broker.payload.RemovePartnerRequest;
import cm.g2s.uaa.service.broker.payload.CreateAccountRequest;
import cm.g2s.uaa.service.broker.payload.CreatePartnerRequest;
import cm.g2s.uaa.service.broker.publisher.UserEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service("userEventPublisherService")
public class UserEventPublisherServiceImpl implements UserEventPublisherService {

    private final UserEventSource eventSource;

    @Override
    @TransactionalEventListener
    public void onCreatePartnerEvent(CreatePartnerRequest partnerRequest) {
        log.info("Publishing User Partner creation  to rabbitmq");
        eventSource.partnerCreated().send(MessageBuilder.withPayload(partnerRequest).build());

    }

    @Override
    @TransactionalEventListener
    public void onCreateAccountEvent(CreateAccountRequest accountRequest) {
        log.info("Publishing User Account creation to  rabbitmq");
        eventSource.accountCreated().send(MessageBuilder.withPayload(accountRequest).build());
    }

    @Override
    @TransactionalEventListener
    public void onRemovePartnerEvent(RemovePartnerRequest accountFailedRequest) {
        log.info("Publishing Create Account to rabbitmq");
        eventSource.partnerRemoved().send(MessageBuilder.withPayload(accountFailedRequest).build());
    }
}
