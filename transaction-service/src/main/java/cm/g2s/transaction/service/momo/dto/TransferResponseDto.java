package cm.g2s.transaction.service.momo.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto implements Serializable {

    private static final long serialVersionUID = 4159626656206077360L;


    private String amount;
    private String currency;
    private String financialTransactionId;
    private String externalId;
    private PayeeDto payee;
    private String payerMessage;
    private String payeeNote;
    private String status;
    private ReasonDto reason;
}