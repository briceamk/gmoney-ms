package cm.g2s.uaa.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface UaaEventSource {



    // Output channel
    @Output(value = "uaaChannel")
    MessageChannel   uaaChannel();

    //Input channel
    @Input(value = "partnerChannel")
    SubscribableChannel partnerChannel();

    @Input(value = "accountChannel")
    SubscribableChannel accountChannel();

}
