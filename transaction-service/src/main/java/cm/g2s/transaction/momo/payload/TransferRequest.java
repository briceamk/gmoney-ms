package cm.g2s.transaction.momo.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class TransferRequest implements Serializable {

    private static final long serialVersionUID = 1202903486667855652L;


    @NotEmpty(message = "amount is required")
    private String amount;
    @NotEmpty(message = "currency is required")
    private String currency;
    @NotEmpty(message = "externalId is required")
    private String externalId;
    @NotNull(message = "payee is required")
    private Payee payee;
    @NotEmpty(message = "payer message is required")
    private String payerMessage;
    @NotEmpty(message = "payer note is required")
    private String payeeNote;

}
