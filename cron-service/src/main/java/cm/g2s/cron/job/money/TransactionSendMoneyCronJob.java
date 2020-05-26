package cm.g2s.cron.job.money;

import cm.g2s.cron.infratructure.broker.CronEventSource;
import cm.g2s.cron.job.JobEventType;
import cm.g2s.cron.job.money.payload.SendMoneyRequest;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.quartz.QuartzJobBean;


@Slf4j
@DisallowConcurrentExecution
public class TransactionSendMoneyCronJob extends QuartzJobBean {

    private CronEventSource cronEventSource;

    @Autowired
    public void setCronEventSource(CronEventSource cronEventSource) {
        this.cronEventSource = cronEventSource;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("Transaction SendMoney Cron Job Start................");
        cronEventSource.sendMoney().send(MessageBuilder.withPayload(
                SendMoneyRequest.builder()
                        .eventType(JobEventType.SEND_MONEY)
                        .jobId(context.getFireInstanceId())
                        .build()
        ).build());
        log.info("Transaction SendMoney Cron Job End................");
    }
}
