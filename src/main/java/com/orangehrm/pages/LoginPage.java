package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import com.orangehrm.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final By USERNAME = By.name("username");
    private static final By PASSWORD = By.name("password");
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");
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

    public String getErrorAlertText() {
        return getText(ERROR_ALERT);
    }

    public String getUsernameRequiredText() {
        return getText(USERNAME_REQUIRED);
    }

    public String getPasswordRequiredText() {
        return getText(PASSWORD_REQUIRED);
    }

    public String getLoginHeadingText() {
        return getText(LOGIN_HEADING);
    }

    public String getForgotPasswordText() {
        return getText(FORGOT_PASSWORD);
    }

    public String getVersionText() {
        return getText(VERSION_TEXT);
    }

    public String getLoginButtonType() {
        return getAttribute(LOGIN_BUTTON, "type");
    }

    public boolean isOnLoginPage() {
        return driver.getCurrentUrl().contains("auth/login");
    }
}
