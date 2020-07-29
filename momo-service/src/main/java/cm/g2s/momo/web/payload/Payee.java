package cm.g2s.momo.web.payload;


import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payee implements Serializable {

    private static final long serialVersionUID = 5954420428697548792L;

    private String partyId;
    private String partyIdType;
}
