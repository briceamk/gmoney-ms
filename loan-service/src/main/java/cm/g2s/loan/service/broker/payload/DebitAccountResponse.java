package cm.g2s.loan.service.broker.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitAccountResponse {
    private String loanId;
    private Boolean debitAccountError;
}
