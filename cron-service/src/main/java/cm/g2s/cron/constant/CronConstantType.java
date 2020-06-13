package cm.g2s.cron.constant;

public class CronConstantType {

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
    public static final String ROUTING_KEY_EXPRESSION = "cron";

    public static final String ROUTING_KEY_SEND_MAIL = "sendMail";
    public static final String ROUTING_KEY_SEND_MONEY = "sendMoney";
}
