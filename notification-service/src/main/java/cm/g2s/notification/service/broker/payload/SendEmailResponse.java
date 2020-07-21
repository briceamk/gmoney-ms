package cm.g2s.notification.service.broker.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailResponse {
    private String loanId;
    private Boolean sendEmailError;
    private String errorMessage;
}
