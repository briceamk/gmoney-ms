package cm.g2s.cron.job;

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


@Slf4j
@DisallowConcurrentExecution
public class SendMailCronJob extends QuartzJobBean {

    private CronEventSource cronEventSource;

    @Autowired
    public void setCronEventSource(CronEventSource cronEventSource) {
        this.cronEventSource = cronEventSource;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("SendMail Cron Job Start................");
        cronEventSource.sendMail().send(MessageBuilder.withPayload(
                JobRequest.builder()
                        .eventType(JobType.SEND_MAIL)
                        .jobId(context.getFireInstanceId())
                        .build()
        ).build());
        log.info("SendMail Cron Job End................");
    }
}
