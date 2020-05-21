package cm.g2s.loan.constant;

public class LoanConstantType {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final String FULL_NAME = "fullName";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String JTI = "jti";

    public static final String[] PUBLIC_MATCHERS = {
            "/api/v1/auth/**",
            "/actuator/**",
            "/v2/api-docs/**"
    };
    public static final String NEW_LOAN_NUMBER = "NEW";
    public static final String INTEREST_KEY = "interest";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN_TYPE = "Bearer";

    public static final String LOAN_ID_HEADER = "loanId";
    public static final String PRINCIPAL_ID_HEADER = "principal";
}
