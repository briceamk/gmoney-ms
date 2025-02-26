package cm.g2s.uaa.constant;

public class UaaConstantType {

    public static final String DEFAULT_USER_ROLE ="ROLE_USER";

    public static final Integer DEFAULT_PAGE_NUMBER = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 20;

    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String GRANT_TYPE_KEY = "grant_type";


    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String USERNAME = "username";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String USER_ID = "userId";
    public static final String JTI = "jti";

    public static final String[] PUBLIC_MATCHERS = {
            "/api/v1/auth/**",
            "/actuator/**",
            "/v2/api-docs/**"
    };

    public static final String USER_ID_HEADER = "userId";
    public static final String PARTNER_ID_HEADER = "partnerId";


    public static final String ROUTING_KEY_EXPRESSION = "uaa";

    public static final String ROUTING_KEY_CREATE_ACCOUNT = "createAccount";
    public static final String ROUTING_KEY_CREATE_PARTNER = "createPartner";
    public static final String ROUTING_KEY_REMOVE_PARTNER = "removePartner";


}
