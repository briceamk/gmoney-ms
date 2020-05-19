package cm.g2s.account.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface AccountEventSource {
    @Input(value = "accountCreatedChannel")
    SubscribableChannel accountCreated();

    @Output(value = "accountCreatedResponseChannel")
    MessageChannel accountCreatedResponse();
}
