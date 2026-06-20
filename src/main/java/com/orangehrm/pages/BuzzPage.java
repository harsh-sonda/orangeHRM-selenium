package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BuzzPage extends BasePage {

    private static final String BUZZ_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/buzz/viewBuzz";

    private static final By BUZZ_HEADING = By.cssSelector("h6.oxd-text--h6");
    private static final By POST_INPUT = By.cssSelector(".oxd-buzz-post-input");
    private static final By BUZZ_CARD = By.cssSelector(".orangehrm-buzz-post");

    public BuzzPage(WebDriver driver) {
        super(driver);
    }

    public void openBuzz() {
        navigateTo(BUZZ_URL);
    }

    public void assertBuzzHeadingContains(String text) {
        assertTextContains(BUZZ_HEADING, text);
    }

    public void assertPostInputVisible() {
        assertVisible(POST_INPUT);
    }

    public void assertBuzzCardVisible() {
        assertVisible(BUZZ_CARD);
    }
}
