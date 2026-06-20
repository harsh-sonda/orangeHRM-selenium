package com.orangehrm.base;

import com.orangehrm.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getImplicitWaitSeconds()));
    }

    public void navigateTo(String url) {
        driver.get(url);
        wait.until(webDriver -> {
            String currentUrl = webDriver.getCurrentUrl();
            if (currentUrl.contains("chrome-error") || currentUrl.startsWith("data:")) {
                return false;
            }
            if (!webDriver.findElements(By.cssSelector(".oxd-layout-context")).isEmpty()) {
                return true;
            }
            if (!webDriver.findElements(By.cssSelector("h6.orangehrm-admin-access-title")).isEmpty()) {
                return true;
            }
            if (!webDriver.findElements(By.cssSelector("h6, h5.oxd-table-filter-title")).isEmpty()) {
                return true;
            }
            String body = webDriver.findElement(By.tagName("body")).getText();
            return body.length() > 80;
        });
        sleepQuietly(1000);
    }

    protected WebElement findElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return findElement(locator).getText();
    }

    protected String getAttribute(By locator, String attribute) {
        return findElement(locator).getAttribute(attribute);
    }

    protected boolean isDisplayed(By locator) {
        try {
            return findElement(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void assertUrlContains(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
    }

    public void assertUrlContainsAny(String... fragments) {
        wait.until(webDriver -> {
            String url = webDriver.getCurrentUrl();
            for (String fragment : fragments) {
                if (url.contains(fragment)) {
                    return true;
                }
            }
            return false;
        });
    }

    public void assertPageBodyContains(String text) {
        Boolean found = wait.until(webDriver ->
                webDriver.findElement(By.tagName("body")).getText().contains(text));
        if (!Boolean.TRUE.equals(found)) {
            throw new AssertionError("Page body does not contain '" + text + "'");
        }
    }

    protected void ensureTableFilterExpanded() {
        List<WebElement> inputs = driver.findElements(By.cssSelector(".oxd-table-filter input"));
        if (!inputs.isEmpty() && inputs.get(0).isDisplayed()) {
            return;
        }
        click(By.cssSelector(".oxd-table-filter-header-options .--toggle button"));
        sleepQuietly(500);
    }

    public void assertTextContains(By locator, String expected) {
        String actual = getText(locator);
        if (!actual.contains(expected)) {
            throw new AssertionError("Expected text to contain '" + expected + "' but was '" + actual + "'");
        }
    }

    public void assertVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void assertAttributeEquals(By locator, String attribute, String expectedValue) {
        String actual = getAttribute(locator, attribute);
        if (actual == null || !actual.equals(expectedValue)) {
            throw new AssertionError("Expected attribute " + attribute + "=" + expectedValue + " but was " + actual);
        }
    }

    public void assertTableBodyContains(String text) {
        Boolean found = wait.until(webDriver -> {
            WebElement table = webDriver.findElement(By.cssSelector(".oxd-table-body"));
            return table.getText().contains(text);
        });
        if (!Boolean.TRUE.equals(found)) {
            throw new AssertionError("Table does not contain '" + text + "'");
        }
    }

    public void waitMs(long ms) {
        sleepQuietly(ms);
    }

    public static void sleepQuietly(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
