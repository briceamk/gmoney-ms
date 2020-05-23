package cm.g2s.loan.service.broker.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransactionRequest {
    private String originRef;
    private String partnerId;
    private String accountId;
    private String companyId;
    private String userId;
    private String mobile;
    private Timestamp issueDate;
    private BigDecimal amount;
    private String mode;
    private String loanId;


}
