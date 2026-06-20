package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.WebDriver;

public class ClaimPage extends BasePage {

    private static final String CLAIM_MODULE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/claim/viewClaimModule";

    public ClaimPage(WebDriver driver) {
        super(driver);
    }

    public void openClaimModule() {
        navigateTo(CLAIM_MODULE_URL);
    }

    public void assertClaimHeadingContains(String text) {
        assertPageBodyContains(text);
    }
}
