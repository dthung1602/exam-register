package vgu.group1.examregister;

public class Config {
    public static String DB_HOST = getenvOrDefault("DB_HOST", "jdbc:mysql://localhost:3306/examreg");
    public static String DB_USERNAME = getenvOrDefault("DB_USER", "examreguser");
    public static String DB_PASSWORD = getenvOrDefault("DB_PASSWORD", "whatever123");

    public static String TEST_DB_HOST = getenvOrDefault("TEST_DB_HOST", "jdbc:mysql://localhost:3306/examreg");
    public static String TEST_DB_USERNAME = getenvOrDefault("TEST_DB_USER", "examreguser");
    public static String TEST_DB_PASSWORD = getenvOrDefault("TEST_DB_PASSWORD", "whatever123");

    private static String getenvOrDefault(String envVarName, String defaultValue) {
        String envVarValue = System.getenv(envVarName);
        return (envVarValue == null) ? defaultValue : envVarValue;
    }
}
