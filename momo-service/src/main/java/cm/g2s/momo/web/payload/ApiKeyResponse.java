package cm.g2s.momo.web.payload;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyResponse implements Serializable {

    private static final long serialVersionUID = -6132646718958356419L;

    private String apiKey;
}
