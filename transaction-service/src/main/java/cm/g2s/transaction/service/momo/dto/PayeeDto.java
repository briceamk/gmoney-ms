package cm.g2s.transaction.service.momo.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayeeDto implements Serializable {

    private static final long serialVersionUID = 5185610698023787843L;

    private String partyId;
    private String partyIdType;
}
