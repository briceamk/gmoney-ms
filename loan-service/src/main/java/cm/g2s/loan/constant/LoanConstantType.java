package cm.g2s.loan.constant;

public class LoanConstantType {

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
    public static final String NEW_LOAN_NUMBER = "NEW";
    public static final String INTEREST_KEY = "interest";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_TOKEN_TYPE = "Bearer";
    public static final String ACCESS_TOKEN = "accessToken";

    public static final String LOAN_ID_HEADER = "loanId";
    public static final String PRINCIPAL_ID_HEADER = "principal";
    public static final String TRANSACTION_MODE_LOAN = "LOAN";

    public static final String ROUTING_KEY_EXPRESSION = "loan";
    public static final String ROUTING_KEY_CREATE_TRANSACTION = "createTransaction";
    public static final String ROUTING_KEY_DEBIT_ACCOUNT = "debitAccount";
    public static final String ROUTING_KEY_CONFIRM_DEBIT_ACCOUNT = "confirmDebitAccount";
    public static final String ROUTING_KEY_CREATE_SEND_MONEY_SUCCESS_EMAIL = "createSendMoneySuccessEmail";

    public static final String ACCOUNT_BALANCE_HEADER = "balance";

    public static final String ACCOUNT_STATE_PENDING = "PENDING";
}
