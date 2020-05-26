package cm.g2s.cron.job.money.payload;

import cm.g2s.cron.job.JobEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendMoneyRequest {
    private String jobId;
    private JobEventType eventType;
}
