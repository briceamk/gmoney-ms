package cm.g2s.momo.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "momo")
public class MomoApiConfiguration {
    private String ocpApimSubscriptionKey;
    private String xReferenceId;
    private String apiKey;
    private String apiUserUrl;
    private String apiKeyUrl;
    private String tokenUrl;
    private String transferUrl;
    private String transferInfoUrl;
    private String xTargetEnvironment;

}

