package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashboardPage extends BasePage {

    private static final By DASHBOARD_HEADING = By.cssSelector("h6.oxd-text--h6");

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return currentUrlContains("dashboard")
                && getHeadingText().contains("Dashboard");
    }

    public String getHeadingText() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(DASHBOARD_HEADING));
        return getText(DASHBOARD_HEADING);
    }
}
