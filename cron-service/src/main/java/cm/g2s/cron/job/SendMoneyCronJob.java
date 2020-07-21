package cm.g2s.cron.job;

import cm.g2s.cron.constant.CronConstantType;
import cm.g2s.cron.infratructure.broker.CronEventSource;
import cm.g2s.cron.job.payload.JobRequest;
import cm.g2s.cron.job.payload.JobType;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.transaction.event.TransactionalEventListener;


@Slf4j
@DisallowConcurrentExecution
public class SendMoneyCronJob extends QuartzJobBean {

    private CronEventSource cronEventSource;

    @Autowired
    public void setCronEventSource(CronEventSource cronEventSource) {
        this.cronEventSource = cronEventSource;
    }

    @Override
    @TransactionalEventListener
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("SendMoney Cron Job Start................");
        cronEventSource.cronChannel().send(
                MessageBuilder
                        .withPayload(
                            JobRequest.builder()
                                    .eventType(JobType.SEND_MONEY)
                                    .jobId(context.getFireInstanceId())
                                    .build()
                        )
                        .setHeader(CronConstantType.ROUTING_KEY_EXPRESSION, CronConstantType.ROUTING_KEY_SEND_MONEY)
                        .build()
        );
        log.info("SendMoney Cron Job End................");
    }
}
