package com.orangehrm.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        return Integer.parseInt(PROPERTIES.getProperty("implicit.wait.seconds", "15"));
    }

    public static String getBrowser() {
        return PROPERTIES.getProperty("browser", "chrome");
    }

    public static String getAdminUsername() {
        return PROPERTIES.getProperty("admin.username", "Admin");
    }

    public static String getAdminPassword() {
        return PROPERTIES.getProperty("admin.password", "admin123");
    }

    public static boolean isHeadless() {
        String override = System.getProperty("headless");
        if (override != null) {
            return Boolean.parseBoolean(override);
        }
        return Boolean.parseBoolean(PROPERTIES.getProperty("headless", "false"));
    }

    public static Path getProjectRoot() {
        String override = System.getProperty("project.root");
        if (override != null && !override.isBlank()) {
            return Paths.get(override);
        }
        return Paths.get(System.getProperty("user.dir"));
    }

    public static Path getResultsDir() {
        return getProjectRoot().resolve("tests/results");
    }

    public static InputStream openClasspathResource(String resourcePath) {
        InputStream stream = TestConfig.class.getClassLoader().getResourceAsStream(resourcePath);
        if (stream == null) {
            throw new IllegalStateException("Classpath resource not found: " + resourcePath);
        }
        return stream;
    }

    public static String readClasspathResource(String resourcePath) {
        try (InputStream stream = openClasspathResource(resourcePath)) {
            return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to read classpath resource: " + resourcePath, e);
        }
    }
}
