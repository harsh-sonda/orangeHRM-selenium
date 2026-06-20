package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class TimeTest extends BaseTest {

    @Test(description = "My Timesheets")
    public void tc_time_001_my_timesheets() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        TimePage timePage = new TimePage(driver);
        timePage.openTimeModule();
        timePage.clickTopbarTimesheets();
        common.clickTopbarLink("My Timesheets");
        sleepQuietly(1500);
        common.assertPageSubheadingContains("My Timesheet");
    }
    @Test(description = "Employee Timesheets")
    public void tc_time_002_employee_timesheets() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        TimePage timePage = new TimePage(driver);
        timePage.openEmployeeTimesheets();
        common.assertPageSubheadingContains("Employee");
    }
    @Test(description = "My Attendance")
    public void tc_time_003_my_attendance() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        TimePage timePage = new TimePage(driver);
        timePage.openTimeModule();
        timePage.clickTopbarAttendance();
        common.clickTopbarLink("My Records");
        sleepQuietly(1500);
        common.assertPageSubheadingContains("Attendance");
    }
    @Test(description = "Punch In Out")
    public void tc_time_004_punch_in_out() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        TimePage timePage = new TimePage(driver);
        timePage.openTimeModule();
        timePage.clickTopbarAttendance();
        common.clickTopbarLink("Punch In/Out");
        sleepQuietly(1500);
        common.assertPageSubheadingContains("Punch");
    }
    @Test(description = "Customers")
    public void tc_time_005_customers() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        TimePage timePage = new TimePage(driver);
        timePage.openCustomers();
        common.assertPageSubheadingContains("Customers");
    }
    @Test(description = "Projects")
    public void tc_time_006_projects() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        TimePage timePage = new TimePage(driver);
        timePage.openProjects();
        common.assertPageSubheadingContains("Projects");
    }
    @Test(description = "Project Reports")
    public void tc_time_007_project_reports() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        TimePage timePage = new TimePage(driver);
        timePage.openProjectReports();
        common.assertPageSubheadingContains("Project");
    }
    @Test(description = "Employee Reports")
    public void tc_time_008_employee_reports() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        TimePage timePage = new TimePage(driver);
        timePage.openEmployeeReports();
        common.assertPageSubheadingContains("Employee");
    }
}
