package vgu.group1.examregister;

import io.jsonwebtoken.SignatureAlgorithm;

public class Config {
    public static final String SECRET_KEY = getenvOrDefault("SECRET_KEY", "eEowkdOOGbM8i41HWhiopf3CpDoQHGq0");

    static final int WEB_PORT = Integer.valueOf(getenvOrDefault("WEB_PORT", "8000"));
    static final String WEB_DIR_LOCATION = "src/main/webapp/";
    static final String CONTEXT_PATH = "/";

    public final static String DB_HOST = getenvOrDefault("DB_HOST", "jdbc:mysql://localhost:3306/examreg");
    public final static String DB_USERNAME = getenvOrDefault("DB_USER", "examreguser");
    public final static String DB_PASSWORD = getenvOrDefault("DB_PASSWORD", "whatever123");

    public final static String TEST_DB_HOST = getenvOrDefault("TEST_DB_HOST", "jdbc:mysql://localhost:3306/examreg");
    public final static String TEST_DB_USERNAME = getenvOrDefault("TEST_DB_USER", "examreguser");
    public final static String TEST_DB_PASSWORD = getenvOrDefault("TEST_DB_PASSWORD", "whatever123");

    public final static String AUTH_COOKIE_NAME = "Authorization";
    public final static int AUTH_COOKIE_MAX_AGE = 3600 * 24 * 3; // 3 days
    public final static SignatureAlgorithm AUTH_SIGN_ALGO = SignatureAlgorithm.HS256;

    public final static String PASS_ID = "$31$";
    public final static String PASS_ALGO = "PBKDF2WithHmacSHA1";
    public final static int PASS_DEFAULT_COST = 8;

    public final static String HTML_BASE_FILE = "base.html";
    public final static String HTML_CONTENT_MARKER = "{{}}";


    private static String getenvOrDefault(String envVarName, String defaultValue) {
        String envVarValue = System.getenv(envVarName);
        return (envVarValue == null) ? defaultValue : envVarValue;
    }
}
