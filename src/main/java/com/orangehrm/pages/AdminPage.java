package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AdminPage extends BasePage {

    private static final String BASE = "https://opensource-demo.orangehrmlive.com/web/index.php/admin/";
    private static final String SYSTEM_USERS_URL = BASE + "viewSystemUsers";
    private static final String GENERAL_INFO_URL = BASE + "viewOrganizationGeneralInformation";
    private static final String LOCATIONS_URL = BASE + "viewLocations";
    private static final String SKILLS_URL = BASE + "viewSkills";
    private static final String EDUCATION_URL = BASE + "viewEducation";
    private static final String LICENSES_URL = BASE + "viewLicenses";
    private static final String NATIONALITY_URL = BASE + "nationality";
    private static final String CORPORATE_BRANDING_URL = BASE + "addTheme";

    private static final By USERNAME_SEARCH = By.xpath(
            "(//div[contains(@class,'oxd-table-filter')]//input)[1]");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void openSystemUsers() {
        navigateTo(SYSTEM_USERS_URL);
        assertPageBodyContains("System Users");
    }

    public void openGeneralInformation() {
        navigateTo(GENERAL_INFO_URL);
    }

    public void openLocations() {
        navigateTo(LOCATIONS_URL);
    }

    public void openSkills() {
        navigateTo(SKILLS_URL);
    }

    public void openEducation() {
        navigateTo(EDUCATION_URL);
    }

    public void openLicenses() {
        navigateTo(LICENSES_URL);
    }

    public void openNationalities() {
        navigateTo(NATIONALITY_URL);
    }

    public void openCorporateBranding() {
        navigateTo(CORPORATE_BRANDING_URL);
    }

    public void searchUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-table-filter")));
        ensureTableFilterExpanded();
        type(USERNAME_SEARCH, username);
    }
}
