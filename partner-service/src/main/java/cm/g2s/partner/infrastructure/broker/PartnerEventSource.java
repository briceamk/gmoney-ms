package cm.g2s.partner.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PartnerEventSource {

    @Input(value = "uaaCreatePartnerChannel")
    SubscribableChannel uaaCreatePartnerChannel();

    @Input(value = "uaaRemovePartnerChannel")
    SubscribableChannel uaaRemovePartnerChannel();

    @Output(value = "partnerChannel")
    MessageChannel partnerChannel();
}
