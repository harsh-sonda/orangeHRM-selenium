package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TimePage extends BasePage {

    private static final String BASE = "https://opensource-demo.orangehrmlive.com/web/index.php/time/";
    private static final String TIME_MODULE_URL = BASE + "viewTimeModule";
    private static final String MY_TIMESHEET_URL = BASE + "viewMyTimesheet";
    private static final String EMPLOYEE_TIMESHEET_URL = BASE + "viewEmployeeTimesheet";
    private static final String MY_ATTENDANCE_URL = BASE + "viewMyAttendance";
    private static final String PUNCH_URL = BASE + "viewPunchInOut";
    private static final String CUSTOMERS_URL = BASE + "viewCustomers";
    private static final String PROJECTS_URL = BASE + "viewProjects";
    private static final String PROJECT_REPORTS_URL = BASE + "viewProjectReport";
    private static final String EMPLOYEE_REPORTS_URL = BASE + "viewEmployeeReport";

    private static final By TOPBAR_TIMESHEETS = By.xpath(
            "//nav[@aria-label='Topbar Menu']//span[normalize-space()='Timesheets']/ancestor::li");
    private static final By TOPBAR_ATTENDANCE = By.xpath(
            "//nav[@aria-label='Topbar Menu']//span[normalize-space()='Attendance']/ancestor::li");
    private static final By TOPBAR_REPORTS = By.xpath(
            "//nav[@aria-label='Topbar Menu']//span[normalize-space()='Reports']/ancestor::li");
    private static final By TOPBAR_PROJECT = By.xpath(
            "//nav[@aria-label='Topbar Menu']//span[normalize-space()='Project Info']/ancestor::li");

    public TimePage(WebDriver driver) {
        super(driver);
    }

    public void openTimeModule() {
        navigateTo(TIME_MODULE_URL);
    }

    public void openMyTimesheet() {
        navigateTo(MY_TIMESHEET_URL);
    }

    public void openEmployeeTimesheets() {
        navigateTo(EMPLOYEE_TIMESHEET_URL);
    }

    public void openMyAttendance() {
        navigateTo(MY_ATTENDANCE_URL);
    }

    public void openPunchInOut() {
        navigateTo(PUNCH_URL);
    }

    public void openCustomers() {
        navigateTo(CUSTOMERS_URL);
    }

    public void openProjects() {
        navigateTo(PROJECTS_URL);
    }

    public void openProjectReports() {
        navigateTo(PROJECT_REPORTS_URL);
    }

    public void openEmployeeReports() {
        navigateTo(EMPLOYEE_REPORTS_URL);
    }

    public void clickTopbarTimesheets() {
        click(TOPBAR_TIMESHEETS);
        sleepQuietly(500);
    }

    public void clickTopbarAttendance() {
        click(TOPBAR_ATTENDANCE);
        sleepQuietly(500);
    }

    public void clickTopbarReports() {
        click(TOPBAR_REPORTS);
        sleepQuietly(500);
    }

    public void clickTopbarProject() {
        click(TOPBAR_PROJECT);
        sleepQuietly(500);
    }
}
