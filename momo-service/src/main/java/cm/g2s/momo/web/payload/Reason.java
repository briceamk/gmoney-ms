package cm.g2s.momo.web.payload;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reason implements Serializable {

    private static final long serialVersionUID = -2074905139113743018L;

    private int status;
    private String code;
    private String message;
}
