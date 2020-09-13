package cm.g2s.transaction.momo.payload;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse implements Serializable {

    private static final long serialVersionUID = 4159626656206077360L;


    private String amount;
    private String currency;
    private String financialTransactionId;
    private String externalId;
    private Payee payee;
    private String payerMessage;
    private String payeeNote;
    private String status;
    private Reason reason;
}

