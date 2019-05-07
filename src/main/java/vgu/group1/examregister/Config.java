package vgu.group1.examregister;

import io.jsonwebtoken.SignatureAlgorithm;

public class Config {
    static final int PORT = Integer.valueOf(getenvOrDefault("PORT", "8000"));
    static final String WEB_DIR_LOCATION = "src/main/webapp/";
    static final String CONTEXT_PATH = "/";

    public final static int DB_CONNECTION_MAX_TIME_OUT = 5;
    public final static String DB_URL = getenvOrDefault("DB_URL",
            "jdbc:mysql://examreguser:whatever123@localhost/examreg?reconnect=true");
    public final static String TEST_DB_URL = getenvOrDefault("TEST_DB_URL",
            "jdbc:mysql://examreguser:whatever123@localhost/examreg?reconnect=true");

    public static final String SECRET_KEY = getenvOrDefault("SECRET_KEY",
            "eEowkdOOGbM8i41HWhiopf3CpDoQHGq0");
    public final static String AUTH_COOKIE_NAME = "Authorization";
    public final static int AUTH_COOKIE_MAX_AGE = 3600 * 24 * 3; // 3 days
    public final static SignatureAlgorithm AUTH_SIGN_ALGO = SignatureAlgorithm.HS256;

    public final static String PASS_ALGO = "PBKDF2WithHmacSHA1";
    public final static int PASS_DEFAULT_COST = 8;

    public static final float MINIMUM_ATTENDANCE_PERCENT = Float.parseFloat(
            getenvOrDefault("MINIMUM_ATTENDANCE_PERCENT", "0.8"));

    private static String getenvOrDefault(String envVarName, String defaultValue) {
        String envVarValue = System.getenv(envVarName);
        return (envVarValue == null) ? defaultValue : envVarValue;
    }
}
