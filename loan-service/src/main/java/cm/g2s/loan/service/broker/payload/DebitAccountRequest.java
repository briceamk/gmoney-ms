package cm.g2s.loan.service.broker.payload;

import cm.g2s.loan.security.CustomPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebitAccountRequest {
    private String accountId;
    private String loanId;
    private BigDecimal debitAmount;
}
