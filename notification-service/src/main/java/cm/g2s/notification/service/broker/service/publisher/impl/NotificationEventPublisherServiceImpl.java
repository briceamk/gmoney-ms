package cm.g2s.notification.service.broker.service.publisher.impl;

import cm.g2s.notification.constant.NotificationConstantType;
import cm.g2s.notification.infrastructure.broker.NotificationEventSource;
import cm.g2s.notification.service.broker.payload.CreateSendMoneySuccessEmailResponse;
import cm.g2s.notification.service.broker.service.publisher.NotificationEventPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Service("notificationEventPublisherService")
public class NotificationEventPublisherServiceImpl implements NotificationEventPublisherService {

    private final NotificationEventSource eventSource;

    @Override
    @TransactionalEventListener
    public void onCreateSendMoneySuccessEmailResponseEvent(CreateSendMoneySuccessEmailResponse createSendMoneySuccessEmailResponse) {
        log.info("Sending notification create send money success email loan-service ");
        eventSource.notificationChannel().send(
                MessageBuilder
                        .withPayload(createSendMoneySuccessEmailResponse)
                        .setHeader(NotificationConstantType.ROUTING_KEY_EXPRESSION, NotificationConstantType.ROUTING_KEY_CREATE_SEND_MONEY_SUCCESS_EMAIL_RESPONSE)
                        .build()
        );
    }
}
