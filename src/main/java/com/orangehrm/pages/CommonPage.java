package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CommonPage extends BasePage {

    private static final By USER_MENU = By.cssSelector(".oxd-userdropdown-tab");
    private static final By LOGOUT_LINK = By.xpath("//a[contains(@href,'auth/logout')]");
    private static final By PAGE_HEADING = By.cssSelector("h6.oxd-text--h6");
    private static final By PAGE_SUBHEADING = By.cssSelector("h6.orangehrm-main-title");
    private static final By SIDEBAR_MENU = By.cssSelector(".oxd-main-menu-item");
    private static final By SEARCH_BUTTON = By.xpath("//button[normalize-space()='Search']");
    private static final By RESET_BUTTON = By.cssSelector("button[type='reset']");
    private static final By TABLE_BODY = By.cssSelector(".oxd-table-body");
    private static final By TOAST_MESSAGE = By.cssSelector(".oxd-text--toast");
    private static final By TOPBAR_USER_MANAGEMENT = By.xpath(
            "//nav[@aria-label='Topbar Menu']//span[normalize-space()='User Management']/ancestor::li");
    private static final By TOPBAR_JOB = By.xpath(
            "//nav[@aria-label='Topbar Menu']//span[normalize-space()='Job']/ancestor::li");
    private static final By TOPBAR_ORGANIZATION = By.xpath(
            "//nav[@aria-label='Topbar Menu']//span[normalize-space()='Organization']/ancestor::li");
    private static final By TOPBAR_QUALIFICATIONS = By.xpath(
            "//nav[@aria-label='Topbar Menu']//span[normalize-space()='Qualifications']/ancestor::li");

    public CommonPage(WebDriver driver) {
        super(driver);
    }

    public void openUserMenu() {
        click(USER_MENU);
    }

    public void logout() {
        openUserMenu();
        click(LOGOUT_LINK);
    }

    public void assertLogoutLinkVisible() {
        assertTextContains(LOGOUT_LINK, "Logout");
    }

    public void assertOnLoginPage() {
        assertUrlContains("auth/login");
    }

    public void assertSidebarVisible() {
        assertVisible(SIDEBAR_MENU);
    }

    public void assertSidebarContains(String menuText) {
        List<WebElement> items = driver.findElements(SIDEBAR_MENU);
        boolean found = items.stream().anyMatch(item -> item.getText().contains(menuText));
        if (!found) {
            throw new AssertionError("Sidebar does not contain '" + menuText + "'");
        }
    }

    public void clickSearch() {
        click(SEARCH_BUTTON);
    }

    public void clickReset() {
        click(RESET_BUTTON);
    }

    public void assertPageHeadingContains(String text) {
        assertTextContains(PAGE_HEADING, text);
    }

    public void assertPageSubheadingContains(String text) {
        Boolean found = wait.until(webDriver -> {
            for (WebElement element : webDriver.findElements(PAGE_SUBHEADING)) {
                if (element.getText().contains(text)) {
                    return true;
                }
            }
            for (WebElement element : webDriver.findElements(By.cssSelector("h5.oxd-table-filter-title"))) {
                if (element.getText().contains(text)) {
                    return true;
                }
            }
            for (WebElement element : webDriver.findElements(
                    By.cssSelector("h6.orangehrm-main-title, h6.oxd-text--h6:not(.oxd-topbar-header-breadcrumb-module)"))) {
                if (element.getText().contains(text)) {
                    return true;
                }
            }
            return webDriver.findElement(By.tagName("body")).getText().contains(text);
        });
        if (!Boolean.TRUE.equals(found)) {
            throw new AssertionError("Page does not contain heading text '" + text + "'");
        }
    }

    public void assertTableVisible() {
        assertVisible(TABLE_BODY);
    }

    public void assertTableContains(String text) {
        assertTableBodyContains(text);
    }

    public void assertToastContains(String text) {
        assertTextContains(TOAST_MESSAGE, text);
    }

    public void clickTopbarUserManagement() {
        click(TOPBAR_USER_MANAGEMENT);
        sleepQuietly(500);
    }

    public void clickTopbarJob() {
        click(TOPBAR_JOB);
        sleepQuietly(500);
    }

    public void clickTopbarOrganization() {
        click(TOPBAR_ORGANIZATION);
        sleepQuietly(500);
    }

    public void clickTopbarQualifications() {
        click(TOPBAR_QUALIFICATIONS);
        sleepQuietly(500);
    }

    public void clickTopbarLink(String linkText) {
        By link = By.xpath("//nav[@aria-label='Topbar Menu']//*[@role='menuitem' and contains(., '"
                + linkText + "')]");
        click(link);
        sleepQuietly(500);
    }
}
