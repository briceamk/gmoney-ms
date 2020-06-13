package cm.g2s.account.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;



public interface AccountEventSource {
    @Input(value = "uaaChannel")
    SubscribableChannel uaaChannel();

    @Input(value = "loanChannel")
    SubscribableChannel loanChannel();


    @Output(value = "accountChannel")
    MessageChannel accountChannel();
}
