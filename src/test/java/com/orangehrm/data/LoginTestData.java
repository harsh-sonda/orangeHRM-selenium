package com.orangehrm.data;

public class LoginTestData {

    private final String testId;
    private final String testName;
    private final String username;
    private final String password;
    private final String scenario;
    private final String expectedUrlContains;
    private final String expectedText;
    private final String expectedTextTarget;

    public LoginTestData(String testId, String testName, String username, String password,
                         String scenario, String expectedUrlContains, String expectedText,
                         String expectedTextTarget) {
        this.testId = testId;
        this.testName = testName;
        this.username = username;
        this.password = password;
        this.scenario = scenario;
        this.expectedUrlContains = expectedUrlContains;
        this.expectedText = expectedText;
        this.expectedTextTarget = expectedTextTarget;
    }

    public String getTestId() {
        return testId;
    }

    public String getTestName() {
        return testName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getScenario() {
        return scenario;
    }

    public String getExpectedUrlContains() {
        return expectedUrlContains;
    }

    public String getExpectedText() {
        return expectedText;
    }

    public String getExpectedTextTarget() {
        return expectedTextTarget;
    }

    public boolean hasUsername() {
        return username != null && !username.isBlank();
    }

    public boolean hasPassword() {
        return password != null && !password.isBlank();
    }

    @Override
    public String toString() {
        return testId + " - " + testName;
    }
}
