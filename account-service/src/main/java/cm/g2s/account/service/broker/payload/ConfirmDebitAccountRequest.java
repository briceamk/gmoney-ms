package cm.g2s.account.service.broker.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmDebitAccountRequest {
    private String loanId;
    private String accountId;
    private String nextAccountState;
}
