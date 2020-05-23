package cm.g2s.account.service.broker.payload;

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
