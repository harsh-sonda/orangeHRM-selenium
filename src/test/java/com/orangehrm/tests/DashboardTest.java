package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class DashboardTest extends BaseTest {

    @Test(description = "Dashboard heading")
    public void tc_dash_001_dashboard_heading() {
        loginAsAdmin();
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.assertDashboardHeadingContains("Dashboard");
    }
    @Test(description = "Time at Work widget")
    public void tc_dash_002_time_at_work_widget() {
        loginAsAdmin();
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.assertTimeAtWorkWidgetContains("Time at Work");
    }
    @Test(description = "Leave widget")
    public void tc_dash_003_leave_widget() {
        loginAsAdmin();
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.assertLeaveWidgetContains("Leave");
    }
    @Test(description = "Buzz widget")
    public void tc_dash_004_buzz_widget() {
        loginAsAdmin();
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.assertBuzzWidgetContains("Buzz");
    }
    @Test(description = "Quick Launch")
    public void tc_dash_005_quick_launch() {
        loginAsAdmin();
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.assertQuickLaunchVisible();
        dashboardPage.assertQuickLaunchContains("Leave");
    }
}
