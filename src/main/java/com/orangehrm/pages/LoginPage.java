package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import com.orangehrm.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    private static final By USERNAME = By.name("username");
    private static final By PASSWORD = By.name("password");
    private static final By LOGIN_BUTTON = By.xpath("//button[@type='submit' and contains(.,'Login')]");
    private static final By ERROR_ALERT = By.cssSelector(".oxd-alert-content-text");
    private static final By LOGIN_HEADING = By.cssSelector(".orangehrm-login-title");
    private static final By FORGOT_PASSWORD = By.cssSelector(".orangehrm-login-forgot-header");
    private static final By VERSION_TEXT = By.cssSelector(".orangehrm-copyright");
    private static final By USERNAME_REQUIRED = By.xpath(
            "//label[text()='Username']/ancestor::div[contains(@class,'oxd-input-group')]"
                    + "//span[contains(@class,'oxd-input-field-error-message')]");
    private static final By PASSWORD_REQUIRED = By.xpath(
            "//label[text()='Password']/ancestor::div[contains(@class,'oxd-input-group')]"
                    + "//span[contains(@class,'oxd-input-field-error-message')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public LoginPage open() {
        driver.get(TestConfig.getBaseUrl());
        sleepQuietly(500);
        return this;
    }

    public LoginPage enterUsername(String username) {
        type(USERNAME, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        type(PASSWORD, password);
        return this;
    }

    public LoginPage clickLogin() {
        click(LOGIN_BUTTON);
        return this;
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    public void waitForLoginResult() {
        sleepQuietly(2000);
    }

    public void assertErrorAlertContains(String text) {
        assertTextContains(ERROR_ALERT, text);
    }

    public void assertUsernameRequiredContains(String text) {
        assertTextContains(USERNAME_REQUIRED, text);
    }

    public void assertPasswordRequiredContains(String text) {
        assertTextContains(PASSWORD_REQUIRED, text);
    }

    public void assertLoginHeadingContains(String text) {
        assertTextContains(LOGIN_HEADING, text);
    }

    public void assertForgotPasswordContains(String text) {
        assertTextContains(FORGOT_PASSWORD, text);
    }

    public void assertVersionTextContains(String text) {
        assertTextContains(VERSION_TEXT, text);
    }

    public void assertLoginButtonTypeSubmit() {
        assertAttributeEquals(LOGIN_BUTTON, "type", "submit");
    }

    public void assertOnLoginPage() {
        wait.until(ExpectedConditions.urlContains("auth/login"));
    }
}
