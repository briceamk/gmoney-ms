package cm.g2s.uaa.constant;

public class UaaConstantType {

    public static final String DEFAULT_USER_ROLE ="ROLE_USER";

    public static final Integer DEFAULT_PAGE_NUMBER = 0;
    public static final Integer DEFAULT_PAGE_SIZE = 20;

    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String GRANT_TYPE_KEY = "grant_type";


    public static final String FULL_NAME = "fullName";
    public static final String USERNAME = "username";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String JTI = "jti";

    public static final String[] PUBLIC_MATCHERS = {"/api/v1/auth/**", "/actuator/**", "/v2/api-docs/**"};

}
