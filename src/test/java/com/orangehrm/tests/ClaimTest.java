package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class ClaimTest extends BaseTest {

    @Test(description = "Claim module")
    public void tc_claim_001_claim_module() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        ClaimPage claimPage = new ClaimPage(driver);
        claimPage.openClaimModule();
        new CommonPage(driver).assertUrlContainsAny("viewAssignClaim", "viewClaimModule");
        claimPage.assertClaimHeadingContains("Claim");
    }
}
