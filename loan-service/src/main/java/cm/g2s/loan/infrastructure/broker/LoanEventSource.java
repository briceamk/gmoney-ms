package cm.g2s.loan.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface LoanEventSource {

    @Output(value = "loanChannel")
    MessageChannel loanChannel();

    @Input(value = "accountDebitAccountResponseChannel")
    SubscribableChannel accountDebitAccountResponseChannel();

    @Input(value = "accountConfirmDebitAccountResponseChannel")
    SubscribableChannel accountConfirmDebitAccountResponseChannel();

    @Input(value = "transactionCreateTransactionResponseChannel")
    SubscribableChannel transactionCreateTransactionResponseChannel();

    @Input(value = "transactionSendMoneyResponseChannel")
    SubscribableChannel transactionSendMoneyResponseChannel();

}
