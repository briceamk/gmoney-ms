package cm.g2s.transaction.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface TransactionEventSource {

    @Input(value = "loanCreateTransactionChannel")
    SubscribableChannel loanCreateTransactionChannel();

    @Input(value = "cronSendMoneyChannel")
    SubscribableChannel cronSendMoneyChannel();

    @Output(value = "transactionChannel")
    MessageChannel transactionChannel();

}
