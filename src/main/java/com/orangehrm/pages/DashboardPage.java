package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage {

    private static final By DASHBOARD_HEADING = By.cssSelector("h6.oxd-text--h6");
    private static final By QUICK_LAUNCH = By.cssSelector(".orangehrm-quick-launch");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public void waitForDashboard() {
        wait.until(ExpectedConditions.urlContains("dashboard"));
        sleepQuietly(1500);
    }

    public void assertDashboardHeadingContains(String text) {
        waitForDashboard();
        assertTextContains(DASHBOARD_HEADING, text);
    }

    public void assertDashboardBodyContains(String text) {
        waitForDashboard();
        Boolean found = wait.until(webDriver ->
                webDriver.findElement(By.tagName("body")).getText().contains(text));
        if (!Boolean.TRUE.equals(found)) {
            throw new AssertionError("Dashboard does not contain '" + text + "'");
        }
    }

    public void assertTimeAtWorkWidgetContains(String text) {
        assertDashboardBodyContains(text);
    }

    public void assertLeaveWidgetContains(String text) {
        assertDashboardBodyContains(text);
    }

    public void assertBuzzWidgetContains(String text) {
        assertDashboardBodyContains(text);
    }

    public void assertQuickLaunchVisible() {
        waitForDashboard();
        assertVisible(QUICK_LAUNCH);
    }

    public void assertQuickLaunchContains(String text) {
        waitForDashboard();
        assertTextContains(QUICK_LAUNCH, text);
    }
}
