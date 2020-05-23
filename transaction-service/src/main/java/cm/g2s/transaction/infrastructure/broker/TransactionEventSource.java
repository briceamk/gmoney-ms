package cm.g2s.transaction.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TransactionEventSource {

    @Input(value = "transactionCreatedChannel")
    SubscribableChannel transactionCreated();

    @Output(value = "transactionCreatedResponseChannel")
    MessageChannel transactionCreatedResponse();

}
