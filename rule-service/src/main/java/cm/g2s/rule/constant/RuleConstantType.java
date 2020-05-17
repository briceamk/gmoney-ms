package cm.g2s.rule.constant;

public class RuleConstantType {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final String FULL_NAME = "fullName";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String JTI = "jti";

    public static final String[] PUBLIC_MATCHERS = {"/api/v1/auth/**", "/actuator/**", "/v2/api-docs/**"};
}
