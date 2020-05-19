package cm.g2s.uaa.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface UserEventSource {

    // Output channel
    @Output(value = "partnerCreatedChannel")
    MessageChannel partnerCreated();

    @Output(value = "accountCreatedFailedChannel")
    MessageChannel accountCreatedFailed();

    @Output(value = "accountCreatedChannel")
    MessageChannel accountCreated();

    //Input channel
    @Input(value = "partnerCreatedResponseChannel")
    SubscribableChannel partnerCreatedResponse();

    @Input(value = "accountCreatedResponseChannel")
    SubscribableChannel accountCreatedResponse();

}
