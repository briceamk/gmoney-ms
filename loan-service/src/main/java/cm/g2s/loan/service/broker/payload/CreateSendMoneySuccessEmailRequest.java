package cm.g2s.loan.service.broker.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSendMoneySuccessEmailRequest {
    private String loanId;
    private String loanNumber;
    private String type;
    private BigDecimal amount;
    private BigDecimal balance;
    private String fullName;
    private String email;
}
