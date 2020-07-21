package cm.g2s.notification.constant;

public class NotificationConstantType {

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

    public static final String EMAIL_TRANSPORT_PROTOCOL_KEY = "mail.transport.protocol";
    public static final String EMAIL_AUTH_KEY = "mail.smtp.auth";
    public static final String EMAIL_START_TLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String DEFAULT_EMAIL_TEST = "brice.mbiandji@g2snet.com";
    public static final String MAIL_SEPARATOR = ";";

    // MAIL RELATED CLASS

    public static final String LOAN_CLASS = "Loan";

    // MAIL TEMPLATE CONSTANT WHEN SEND MONEY WAS SUCCESSFUL
    public static final String SEND_MONEY_SUCCESS_LOAN_NUMBER = "%loanNumber";
    public static final String SEND_MONEY_SUCCESS_AMOUNT = "%amount";
    public static final String SEND_MONEY_SUCCESS_BALANCE = "%balance";
    public static final String SEND_MONEY_SUCCESS_FULL_NAME = "%fullName";

    public static final String ROUTING_KEY_EXPRESSION = "notification";
    public static final Object ROUTING_KEY_SUCCESS_SEND_MONEY_NOTIFICATION_RESPONSE = "successSendMoneyNotificationResponse";
    public static final Object ROUTING_KEY_CREATE_SEND_MONEY_SUCCESS_EMAIL_RESPONSE = "createSendMoneySuccessEmailResponse";
}
