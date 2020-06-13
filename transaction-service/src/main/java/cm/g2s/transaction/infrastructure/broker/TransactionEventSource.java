package cm.g2s.transaction.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TransactionEventSource {

    @Input(value = "loanChannel")
    SubscribableChannel loanChannel();

    @Input(value = "cronChannel")
    SubscribableChannel cronChannel();

    @Output(value = "transactionChannel")
    MessageChannel transactionChannel();

}
