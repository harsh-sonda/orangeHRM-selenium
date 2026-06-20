package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DirectoryPage extends BasePage {

    private static final String DIRECTORY_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/directory/viewDirectory";

    private static final By DIRECTORY_HEADING = By.cssSelector("h6.oxd-text--h6");
    private static final By DIRECTORY_SEARCH = By.xpath("//input[@placeholder='Search']");
    private static final By DIRECTORY_CARDS = By.cssSelector(".orangehrm-directory-card");

    public DirectoryPage(WebDriver driver) {
        super(driver);
    }

    public void openDirectory() {
        navigateTo(DIRECTORY_URL);
    }

    public void searchDirectory(String query) {
        type(DIRECTORY_SEARCH, query);
        sleepQuietly(1000);
    }

    public void assertDirectoryHeadingContains(String text) {
        assertTextContains(DIRECTORY_HEADING, text);
    }

    public void assertDirectoryCardsVisible() {
        assertVisible(DIRECTORY_CARDS);
    }

    public void assertSearchResultsContain(String text) {
        Boolean found = wait.until(webDriver -> {
            for (WebElement card : webDriver.findElements(DIRECTORY_CARDS)) {
                if (card.getText().contains(text)) {
                    return true;
                }
            }
            return false;
        });
        if (!Boolean.TRUE.equals(found)) {
            throw new AssertionError("Directory cards do not contain '" + text + "'");
        }
    }
}
