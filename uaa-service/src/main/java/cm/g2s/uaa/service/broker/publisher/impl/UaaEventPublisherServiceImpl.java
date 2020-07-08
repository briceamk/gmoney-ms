package cm.g2s.uaa.service.broker.publisher.impl;

import cm.g2s.uaa.constant.UaaConstantType;
import cm.g2s.uaa.infrastructure.broker.UaaEventSource;
import cm.g2s.uaa.service.broker.payload.CreateAccountRequest;
import cm.g2s.uaa.service.broker.payload.CreatePartnerRequest;
import cm.g2s.uaa.service.broker.payload.RemovePartnerRequest;
import cm.g2s.uaa.service.broker.publisher.UaaEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service("userEventPublisherService")
public class UaaEventPublisherServiceImpl implements UaaEventPublisherService {

    private final UaaEventSource eventSource;

    @Override
    @TransactionalEventListener
    public void onCreatePartnerEvent(CreatePartnerRequest createPartnerRequest) {
        log.info("Publishing User Partner creation  to rabbitmq");
        eventSource.uaaChannel().send(
                MessageBuilder
                        .withPayload(createPartnerRequest)
                        .setHeader(UaaConstantType.ROUTING_KEY_EXPRESSION, UaaConstantType.ROUTING_KEY_CREATE_PARTNER)
                        .build()
        );

    }

    @Override
    @TransactionalEventListener
    public void onCreateAccountEvent(CreateAccountRequest createAccountRequest) {
        log.info("Publishing User Account creation to  rabbitmq");
        eventSource.uaaChannel().send(
                MessageBuilder
                        .withPayload(createAccountRequest)
                        .setHeader(UaaConstantType.ROUTING_KEY_EXPRESSION, UaaConstantType.ROUTING_KEY_CREATE_ACCOUNT)
                        .build()
        );
    }

    @Override
    @TransactionalEventListener
    public void onRemovePartnerEvent(RemovePartnerRequest removePartnerRequest) {
        log.info("Publishing Create Account to rabbitmq");
        eventSource.uaaChannel().send(
                MessageBuilder
                        .withPayload(removePartnerRequest)
                        .setHeader(UaaConstantType.ROUTING_KEY_EXPRESSION, UaaConstantType.ROUTING_KEY_REMOVE_PARTNER)
                        .build());
    }
}
