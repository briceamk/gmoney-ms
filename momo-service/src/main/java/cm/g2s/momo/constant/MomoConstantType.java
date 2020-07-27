package cm.g2s.momo.constant;

public class MomoConstantType {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String JTI = "jti";

    public static final String[] PUBLIC_MATCHERS = {
            "/actuator/**",
            "/v2/api-docs/**"
    };
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN_TYPE = "Bearer";
    public static final String ACCESS_TOKEN = "accessToken";

    public static final String OCP_APIM_SUBSCRIPTION_KEY = "Ocp-Apim-Subscription-Key";
    public static final String X_REFERENCE_ID = "X-Reference-Id";

}
