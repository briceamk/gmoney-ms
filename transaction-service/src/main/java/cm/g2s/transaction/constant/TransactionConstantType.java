package cm.g2s.transaction.constant;

public class TransactionConstantType {

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


    public static final String ROUTING_KEY_EXPRESSION = "transaction";

    public static final String ROUTING_KEY_CREATE_TRANSACTION_RESPONSE = "createTransactionResponse";
    public static final String ROUTING_KEY_SEND_MONEY_RESPONSE = "sendMoneyResponse";
    public static final String DEFAULT_CURRENCY_CODE = "EUR";
    public static final String PAYER_MESSAGE = "Dépôt du montant que vous avez solocité.";
}
