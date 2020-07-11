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
    @Input(value = "partnerCreatePartnerResponseChannel")
    SubscribableChannel partnerCreatePartnerResponseChannel();

    @Input(value = "accountCreateAccountResponseChannel")
    SubscribableChannel accountCreateAccountResponseChannel();

}
