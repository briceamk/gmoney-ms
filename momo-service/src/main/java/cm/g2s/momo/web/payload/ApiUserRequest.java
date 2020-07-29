package cm.g2s.momo.web.payload;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserRequest implements Serializable {

    private static final long serialVersionUID = 7138105439815909111L;

    @NotEmpty(message = "provider call back host is required!")
    private String providerCallbackHost;
}
