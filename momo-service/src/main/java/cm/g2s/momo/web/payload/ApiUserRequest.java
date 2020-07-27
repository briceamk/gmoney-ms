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

    @NotEmpty(message = "provider call back host is required!")
    private String providerCallbackHost;
}
