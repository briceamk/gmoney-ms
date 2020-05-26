package cm.g2s.account.infrastructure.broker;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

import java.net.DatagramSocket;


public interface AccountEventSource {
    @Input(value = "accountCreatedChannel")
    SubscribableChannel accountCreated();

    @Input(value = "accountDebitedChannel")
    SubscribableChannel accountDebited();

    @Input(value = "confirmAccountDebitChannel")
    SubscribableChannel confirmAccountDebit();

    @Output(value = "accountCreatedResponseChannel")
    MessageChannel accountCreatedResponse();

    @Output(value = "accountDebitedResponseChannel")
    MessageChannel accountDebitedResponse();

    @Output(value = "confirmAccountDebitResponseChannel")
    MessageChannel confirmAccountDebitResponse();
}
