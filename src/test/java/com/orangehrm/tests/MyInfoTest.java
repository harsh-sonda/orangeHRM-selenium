package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class MyInfoTest extends BaseTest {

    @Test(description = "Personal Details")
    public void tc_myinfo_001_personal_details() {
        loginAsAdmin();
        MyInfoPage myInfoPage = new MyInfoPage(driver);
        myInfoPage.openMyDetails();
        myInfoPage.openPersonalDetailsTab();
        sleepQuietly(1500);
        myInfoPage.assertPersonalDetailsHeadingContains("Personal Details");
    }
    @Test(description = "Contact Details")
    public void tc_myinfo_002_contact_details() {
        loginAsAdmin();
        MyInfoPage myInfoPage = new MyInfoPage(driver);
        myInfoPage.openMyDetails();
        myInfoPage.openContactDetailsTab();
        sleepQuietly(1500);
        myInfoPage.assertContactDetailsHeadingContains("Contact Details");
    }
    @Test(description = "Emergency Contacts")
    public void tc_myinfo_003_emergency_contacts() {
        loginAsAdmin();
        MyInfoPage myInfoPage = new MyInfoPage(driver);
        myInfoPage.openMyDetails();
        myInfoPage.openEmergencyContactsTab();
        sleepQuietly(1500);
        myInfoPage.assertEmergencyContactsHeadingContains("Emergency Contacts");
    }
    @Test(description = "Dependents")
    public void tc_myinfo_004_dependents() {
        loginAsAdmin();
        MyInfoPage myInfoPage = new MyInfoPage(driver);
        myInfoPage.openMyDetails();
        myInfoPage.openDependentsTab();
        sleepQuietly(1500);
        myInfoPage.assertDependentsHeadingContains("Dependents");
    }
}
