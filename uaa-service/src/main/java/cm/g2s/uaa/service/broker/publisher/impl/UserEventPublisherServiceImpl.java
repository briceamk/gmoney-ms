package cm.g2s.uaa.service.broker.publisher.impl;

import cm.g2s.uaa.infrastructure.broker.UserEventSource;
import cm.g2s.uaa.service.broker.publisher.UserEventPublisherService;
import cm.g2s.uaa.shared.dto.UserDto;
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
    public void onCreatePartnerEvent(UserDto userDto) {
        log.info("Publishing User Partner creation  to RabbitMQ");
        eventSource.partnerCreated().send(MessageBuilder.withPayload(userDto).build());

    }

    @Override
    @TransactionalEventListener
    public void onCreateAccountEvent(UserDto userDto) {
        log.info("Publishing User Account creation to  RabbitMQ");
        eventSource.accountCreated().send(MessageBuilder.withPayload(userDto).build());
    }

    @Override
    @TransactionalEventListener
    public void onCreateAccountFailedEvent(UserDto userDto) {
        log.info("Publishing Create Account to RabbitMQ");
        eventSource.accountCreatedFailed().send(MessageBuilder.withPayload(userDto).build());
    }
}
