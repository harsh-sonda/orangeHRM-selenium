package com.orangehrm.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class TestConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = TestConfig.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties not found on classpath");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load config.properties", e);
        }
    }

    private TestConfig() {
    }

    public static String getBaseUrl() {
        return PROPERTIES.getProperty("base.url");
    }

    public static int getImplicitWaitSeconds() {
        return Integer.parseInt(PROPERTIES.getProperty("implicit.wait.seconds", "10"));
    }

    public static String getBrowser() {
        return PROPERTIES.getProperty("browser", "chrome");
    }

    public static boolean isHeadless() {
        String override = System.getProperty("headless");
        if (override != null) {
            return Boolean.parseBoolean(override);
        }
        return Boolean.parseBoolean(PROPERTIES.getProperty("headless", "false"));
    }
}
