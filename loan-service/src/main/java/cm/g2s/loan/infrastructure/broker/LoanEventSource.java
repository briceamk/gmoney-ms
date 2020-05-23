package cm.g2s.loan.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface LoanEventSource {

    @Output(value = "transactionCreatedChannel")
    MessageChannel transactionCreated();

    @Output(value = "accountDebitedChannel")
    MessageChannel accountDebited();

    @Input(value = "accountDebitedResponseChannel")
    SubscribableChannel accountDebitedResponse();

    @Input(value = "transactionCreatedResponseChannel")
    SubscribableChannel transactionCreatedResponse();
}
