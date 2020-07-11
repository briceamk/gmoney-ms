package cm.g2s.notification.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface NotificationEventSource {

    @Input(value = "loanSendMoneyNotificationChannel")
    SubscribableChannel loanSendMoneyNotificationChannel();

    @Input(value = "uaaSendSignUpNotificationChannel")
    SubscribableChannel uaaSendSignUpNotificationChannel();
}
