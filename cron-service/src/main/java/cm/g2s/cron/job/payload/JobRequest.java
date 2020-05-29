package cm.g2s.cron.job.payload;

import cm.g2s.cron.job.payload.JobType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobRequest {
    private String jobId;
    private JobType eventType;
}
