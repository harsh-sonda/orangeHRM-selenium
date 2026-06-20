package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyInfoPage extends BasePage {

    private static final String MY_DETAILS_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewMyDetails";

    private static final By PERSONAL_DETAILS_TAB =
            By.xpath("//div[@role='tab' and normalize-space()='Personal Details']");
    private static final By CONTACT_DETAILS_TAB =
            By.xpath("//div[@role='tab' and normalize-space()='Contact Details']");
    private static final By EMERGENCY_CONTACTS_TAB =
            By.xpath("//div[@role='tab' and normalize-space()='Emergency Contacts']");
    private static final By DEPENDENTS_TAB =
            By.xpath("//div[@role='tab' and normalize-space()='Dependents']");

    public MyInfoPage(WebDriver driver) {
        super(driver);
    }

    public void openMyDetails() {
        navigateTo(MY_DETAILS_URL);
    }

    public void openPersonalDetailsTab() {
        click(PERSONAL_DETAILS_TAB);
        waitMs(1500);
    }

    public void openContactDetailsTab() {
        click(CONTACT_DETAILS_TAB);
        waitMs(1500);
    }

    public void openEmergencyContactsTab() {
        click(EMERGENCY_CONTACTS_TAB);
        waitMs(1500);
    }

    public void openDependentsTab() {
        click(DEPENDENTS_TAB);
        waitMs(1500);
    }

    public void assertPersonalDetailsHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertContactDetailsHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertEmergencyContactsHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertDependentsHeadingContains(String text) {
        assertPageBodyContains(text);
    }
}
