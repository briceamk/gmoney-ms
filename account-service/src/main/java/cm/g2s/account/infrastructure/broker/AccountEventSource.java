package cm.g2s.account.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;



public interface AccountEventSource {
    @Input(value = "uaaCreateAccountChannel")
    SubscribableChannel uaaCreateAccountChannel();

    @Input(value = "loanDebitAccountChannel")
    SubscribableChannel loanDebitAccountChannel();

    @Input(value = "loanConfirmDebitAccountChannel")
    SubscribableChannel loanConfirmDebitAccountChannel();


    @Output(value = "accountChannel")
    MessageChannel accountChannel();
}
