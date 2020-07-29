package cm.g2s.momo.web.payload;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserResponse {

    private static final long serialVersionUID = -2145105239145988277L;

    private String providerCallbackHost;
    private String targetEnvironment;
}
