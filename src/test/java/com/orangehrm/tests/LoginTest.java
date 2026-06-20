package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.data.CsvDataProvider;
import com.orangehrm.data.LoginTestData;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginScenarios", dataProviderClass = CsvDataProvider.class)
    public void loginScenarios(LoginTestData data) {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        loginPage.open();

        switch (data.getScenario()) {
            case "valid_login" -> {
                loginPage.login(data.getUsername(), data.getPassword());
                Assert.assertTrue(dashboardPage.isLoaded(),
                        data.getTestId() + ": expected dashboard to load");
                Assert.assertTrue(dashboardPage.getHeadingText().contains(data.getExpectedText()),
                        data.getTestId() + ": expected dashboard heading to contain " + data.getExpectedText());
            }
            case "invalid_login" -> {
                loginPage.login(data.getUsername(), data.getPassword());
                Assert.assertTrue(loginPage.getErrorAlertText().contains(data.getExpectedText()),
                        data.getTestId() + ": expected error alert");
                Assert.assertTrue(loginPage.isOnLoginPage(),
                        data.getTestId() + ": expected to remain on login page");
            }
            case "empty_username" -> {
                loginPage.enterPassword(data.getPassword()).clickLogin();
                Assert.assertTrue(loginPage.getUsernameRequiredText().contains(data.getExpectedText()),
                        data.getTestId() + ": expected username required validation");
                Assert.assertTrue(loginPage.isOnLoginPage(),
                        data.getTestId() + ": expected to remain on login page");
            }
            case "empty_password" -> {
                loginPage.enterUsername(data.getUsername()).clickLogin();
                Assert.assertTrue(loginPage.getPasswordRequiredText().contains(data.getExpectedText()),
                        data.getTestId() + ": expected password required validation");
                Assert.assertTrue(loginPage.isOnLoginPage(),
                        data.getTestId() + ": expected to remain on login page");
            }
            case "both_empty" -> {
                loginPage.clickLogin();
                Assert.assertTrue(loginPage.getUsernameRequiredText().contains(data.getExpectedText()),
                        data.getTestId() + ": expected username required validation");
                Assert.assertTrue(loginPage.getPasswordRequiredText().contains(data.getExpectedText()),
                        data.getTestId() + ": expected password required validation");
                Assert.assertTrue(loginPage.isOnLoginPage(),
                        data.getTestId() + ": expected to remain on login page");
            }
            case "ui_smoke" -> {
                Assert.assertTrue(loginPage.getLoginHeadingText().contains(data.getExpectedText()),
                        data.getTestId() + ": expected login heading");
                Assert.assertEquals(loginPage.getLoginButtonType(), "submit",
                        data.getTestId() + ": expected login button type submit");
                Assert.assertTrue(loginPage.getForgotPasswordText().contains("Forgot your password?"),
                        data.getTestId() + ": expected forgot password text");
                Assert.assertTrue(loginPage.getVersionText().contains("OrangeHRM"),
                        data.getTestId() + ": expected OrangeHRM branding");
                Assert.assertTrue(loginPage.isOnLoginPage(),
                        data.getTestId() + ": expected to be on login page");
            }
            default -> throw new IllegalArgumentException(
                    data.getTestId() + ": unknown scenario " + data.getScenario());
        }
    }
}
