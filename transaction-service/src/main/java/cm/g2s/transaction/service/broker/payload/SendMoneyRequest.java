package cm.g2s.transaction.service.broker.payload;

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
