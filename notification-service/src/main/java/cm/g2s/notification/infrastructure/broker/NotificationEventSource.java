package cm.g2s.notification.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface NotificationEventSource {

    @Input(value = "loanCreateSendMoneySuccessEmailChannel")
    SubscribableChannel loanCreateSendMoneySuccessEmailChannel();

    @Input(value = "uaaSendSignUpEmailSuccessChannel")
    SubscribableChannel uaaSendSignUpEmailSuccessChannel();

    @Input(value = "cronSendEmailChannel")
    SubscribableChannel cronSendEmailChannel();

    @Output(value = "notificationChannel")
    MessageChannel notificationChannel();
}
