package cm.g2s.transaction.service.momo.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReasonDto implements Serializable {

    private static final long serialVersionUID = -276227245877866728L;


    private int status;
    private String code;
    private String message;
}
