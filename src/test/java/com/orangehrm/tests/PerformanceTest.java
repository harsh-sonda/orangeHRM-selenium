package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class PerformanceTest extends BaseTest {

    @Test(description = "Manage Reviews")
    public void tc_perf_001_manage_reviews() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PerformancePage performancePage = new PerformancePage(driver);
        performancePage.openManageReviews();
        new CommonPage(driver).assertUrlContains("searchEvaluatePerformanceReview");
        performancePage.assertManageReviewsHeadingContains("Manage");
    }
    @Test(description = "My Trackers")
    public void tc_perf_002_my_trackers() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PerformancePage performancePage = new PerformancePage(driver);
        performancePage.openMyTrackers();
        new CommonPage(driver).assertUrlContains("viewMyPerformanceTrackerList");
        performancePage.assertMyTrackersHeadingContains("Tracker");
    }
    @Test(description = "Employee Trackers")
    public void tc_perf_003_employee_trackers() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PerformancePage performancePage = new PerformancePage(driver);
        performancePage.openEmployeeTrackers();
        new CommonPage(driver).assertUrlContains("viewEmployeePerformanceTrackerList");
        performancePage.assertEmployeeTrackersHeadingContains("Tracker");
    }
    @Test(description = "KPI list")
    public void tc_perf_004_kpi_list() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PerformancePage performancePage = new PerformancePage(driver);
        performancePage.openKpiList();
        new CommonPage(driver).assertUrlContains("searchKpi");
        performancePage.assertKpiHeadingContains("Performance");
    }
    @Test(description = "Performance module")
    public void tc_perf_005_performance_module() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PerformancePage performancePage = new PerformancePage(driver);
        performancePage.openPerformanceModule();
        new CommonPage(driver).assertUrlContainsAny("searchEvaluatePerformanceReview", "viewPerformanceModule");
        common.assertPageHeadingContains("Performance");
    }
}
