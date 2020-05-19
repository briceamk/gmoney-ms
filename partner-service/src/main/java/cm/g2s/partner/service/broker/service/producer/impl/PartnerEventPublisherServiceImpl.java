package cm.g2s.partner.service.broker.service.producer.impl;

import cm.g2s.partner.infrastructure.broker.PartnerEventSource;
import cm.g2s.partner.service.broker.payload.CreatePartnerResponse;
import cm.g2s.partner.service.broker.service.producer.PartnerEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service("partnerEventPublisherService")
public class PartnerEventPublisherServiceImpl implements PartnerEventPublisherService {

    private final PartnerEventSource eventSource;

    @Override
    @TransactionalEventListener
    public void onCreatePartnerResponseEvent(CreatePartnerResponse response) {
        log.info("Sending creation partner response to uaa-service");
        eventSource.partnerCreatedResponse().send(MessageBuilder.withPayload(response).build());

    }
}
