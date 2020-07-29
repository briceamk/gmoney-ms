package cm.g2s.momo.web.payload;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse implements Serializable {

    private static final long serialVersionUID = 9088665852295706707L;

    private String access_token;
    private String token_type;
    private Long expires_in;
}
