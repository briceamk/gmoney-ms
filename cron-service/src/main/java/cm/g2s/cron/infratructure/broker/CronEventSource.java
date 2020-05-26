package cm.g2s.cron.infratructure.broker;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CronEventSource {

    @Output("sendMoneyChannel")
    MessageChannel sendMoney();

}
