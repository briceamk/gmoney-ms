package cm.g2s.account.service.broker.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmDebitAccountResponse {
    private String loanId;
    private Boolean confirmDebitAccountError;
    private BigDecimal balance;
}
