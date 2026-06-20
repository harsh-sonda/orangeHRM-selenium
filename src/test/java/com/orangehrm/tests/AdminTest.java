package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class AdminTest extends BaseTest {

    @Test(description = "System Users search")
    public void tc_admin_001_system_users_search() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        AdminPage adminPage = new AdminPage(driver);
        adminPage.openSystemUsers();
        adminPage.searchUsername("Admin");
        common.clickSearch();
        common.assertTableContains("Admin");
    }
    @Test(description = "Job Titles")
    public void tc_admin_002_job_titles() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewAdminModule");
        sleepQuietly(500);
        common.clickTopbarJob();
        common.clickTopbarLink("Job Titles");
        sleepQuietly(1500);
        common.assertPageSubheadingContains("Job Titles");
    }
    @Test(description = "Pay Grades")
    public void tc_admin_003_pay_grades() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewAdminModule");
        sleepQuietly(500);
        common.clickTopbarJob();
        common.clickTopbarLink("Pay Grades");
        sleepQuietly(1500);
        common.assertPageSubheadingContains("Pay Grades");
    }
    @Test(description = "Job Categories")
    public void tc_admin_004_job_categories() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewAdminModule");
        sleepQuietly(500);
        common.clickTopbarJob();
        common.clickTopbarLink("Job Categories");
        sleepQuietly(1500);
        common.assertPageSubheadingContains("Job Categories");
    }
    @Test(description = "Work Shifts")
    public void tc_admin_005_work_shifts() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/admin/viewAdminModule");
        sleepQuietly(500);
        common.clickTopbarJob();
        common.clickTopbarLink("Work Shifts");
        sleepQuietly(1500);
        common.assertPageSubheadingContains("Work Shifts");
    }
    @Test(description = "General Information")
    public void tc_admin_006_general_information() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        AdminPage adminPage = new AdminPage(driver);
        adminPage.openGeneralInformation();
        common.assertPageSubheadingContains("General Information");
    }
    @Test(description = "Locations")
    public void tc_admin_007_locations() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        AdminPage adminPage = new AdminPage(driver);
        adminPage.openLocations();
        common.assertPageSubheadingContains("Locations");
    }
    @Test(description = "Skills")
    public void tc_admin_008_skills() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        AdminPage adminPage = new AdminPage(driver);
        adminPage.openSkills();
        common.assertPageSubheadingContains("Skills");
    }
    @Test(description = "Education")
    public void tc_admin_009_education() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        AdminPage adminPage = new AdminPage(driver);
        adminPage.openEducation();
        common.assertPageSubheadingContains("Education");
    }
    @Test(description = "Licenses")
    public void tc_admin_010_licenses() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        AdminPage adminPage = new AdminPage(driver);
        adminPage.openLicenses();
        common.assertPageSubheadingContains("Licenses");
    }
    @Test(description = "Nationalities")
    public void tc_admin_011_nationalities() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        AdminPage adminPage = new AdminPage(driver);
        adminPage.openNationalities();
        common.assertPageSubheadingContains("Nationalities");
    }
    @Test(description = "Corporate Branding")
    public void tc_admin_012_corporate_branding() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        AdminPage adminPage = new AdminPage(driver);
        adminPage.openCorporateBranding();
        common.assertPageSubheadingContains("Corporate Branding");
    }
}
