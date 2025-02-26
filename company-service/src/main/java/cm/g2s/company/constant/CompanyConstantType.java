package cm.g2s.company.constant;

public class CompanyConstantType {

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final String DEFAULT_COMPANY_LOGO_FILE_NAME = "no_company_image.png";
    public static final String DEFAULT_COMPANY_LOGO_IMAGE_TYPE = "image/png";

    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String JTI = "jti";

    public static final String[] PUBLIC_MATCHERS = {
            "/api/v1/auth/**",
            "/actuator/**",
            "/v2/api-docs/**",
            "/api/v1/companies/code/**"
    };
}
