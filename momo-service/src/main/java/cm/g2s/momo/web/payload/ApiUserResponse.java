package cm.g2s.momo.web.payload;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiUserResponse {
    private String providerCallbackHost;
    private String targetEnvironment;
}
