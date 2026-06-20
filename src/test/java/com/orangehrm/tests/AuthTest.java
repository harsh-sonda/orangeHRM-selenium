package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class AuthTest extends BaseTest {

    @Test(description = "Valid login")
    public void tc_login_001_valid_login() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);
        loginPage.open();
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        loginPage.waitForLoginResult();
        dashboardPage.assertDashboardHeadingContains("Dashboard");
    }
    @Test(description = "Invalid credentials")
    public void tc_login_002_invalid_credentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.enterUsername("Admin");
        loginPage.enterPassword("wrongpassword");
        loginPage.clickLogin();
        loginPage.waitForLoginResult();
        loginPage.assertErrorAlertContains("Invalid credentials");
        new CommonPage(driver).assertUrlContains("auth/login");
    }
    @Test(description = "Empty username")
    public void tc_login_003_empty_username() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.enterPassword("admin123");
        loginPage.clickLogin();
        loginPage.waitForLoginResult();
        loginPage.assertUsernameRequiredContains("Required");
    }
    @Test(description = "Empty password")
    public void tc_login_004_empty_password() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.enterUsername("Admin");
        loginPage.clickLogin();
        loginPage.waitForLoginResult();
        loginPage.assertPasswordRequiredContains("Required");
    }
    @Test(description = "Both fields empty")
    public void tc_login_005_both_fields_empty() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.clickLogin();
        loginPage.waitForLoginResult();
        loginPage.assertUsernameRequiredContains("Required");
        loginPage.assertPasswordRequiredContains("Required");
    }
    @Test(description = "UI smoke")
    public void tc_login_006_ui_smoke() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.assertLoginHeadingContains("Login");
        loginPage.assertLoginButtonTypeSubmit();
        loginPage.assertForgotPasswordContains("Forgot your password?");
        loginPage.assertVersionTextContains("OrangeHRM");
    }
}
