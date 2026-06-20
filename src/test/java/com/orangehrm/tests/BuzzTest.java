package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class BuzzTest extends BaseTest {

    @Test(description = "Buzz feed")
    public void tc_buzz_001_buzz_feed() {
        loginAsAdmin();
        BuzzPage buzzPage = new BuzzPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/buzz/viewBuzz");
        sleepQuietly(500);
        buzzPage.assertBuzzHeadingContains("Buzz");
    }
    @Test(description = "Post composer")
    public void tc_buzz_002_post_composer() {
        loginAsAdmin();
        BuzzPage buzzPage = new BuzzPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/buzz/viewBuzz");
        sleepQuietly(500);
        buzzPage.assertPostInputVisible();
    }
    @Test(description = "Buzz post card")
    public void tc_buzz_003_buzz_post_card() {
        loginAsAdmin();
        BuzzPage buzzPage = new BuzzPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/buzz/viewBuzz");
        sleepQuietly(500);
        buzzPage.assertBuzzCardVisible();
    }
}
