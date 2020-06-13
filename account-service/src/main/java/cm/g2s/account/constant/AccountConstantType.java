package cm.g2s.account.constant;

public class AccountConstantType {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String JTI = "jti";

    public static final String[] PUBLIC_MATCHERS = {
            "/api/v1/auth/**",
            "/actuator/**",
            "/v2/api-docs/**"
    };

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN_TYPE = "Bearer";

    public static final String ROUTING_KEY_EXPRESSION = "account";

    public static final String ROUTING_KEY_CREATE_ACCOUNT_RESPONSE = "createAccountResponse";
    public static final String ROUTING_KEY_DEBIT_ACCOUNT_RESPONSE = "debitAccountResponse";
    public static final String ROUTING_KEY_CONFIRM_DEBIT_ACCOUNT_RESPONSE = "confirmDebitAccountResponse";
}
