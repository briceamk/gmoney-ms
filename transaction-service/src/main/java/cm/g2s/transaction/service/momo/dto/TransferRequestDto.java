package cm.g2s.transaction.service.momo.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto implements Serializable {

    private static final long serialVersionUID = 6432460711275959392L;

    @NotEmpty(message = "amount is required")
    private String amount;
    @NotEmpty(message = "currency is required")
    private String currency;
    @NotEmpty(message = "externalId is required")
    private String externalId;
    @NotNull(message = "payee is required")
    private PayeeDto payee;
    @NotEmpty(message = "payer message is required")
    private String payerMessage;
    @NotEmpty(message = "payer note is required")
    private String payeeNote;
}
