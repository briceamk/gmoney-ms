package cm.g2s.account.service.broker.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContext;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DebitAccount {
    private String accountId;
    private String loanId;
    private BigDecimal debitAmount;
    private SecurityContext context;
}
