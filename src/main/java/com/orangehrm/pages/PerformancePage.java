package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.WebDriver;

public class PerformancePage extends BasePage {

    private static final String MANAGE_REVIEWS_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/performance/searchEvaluatePerformanceReview";
    private static final String MY_TRACKERS_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/performance/viewMyPerformanceTrackerList";
    private static final String EMPLOYEE_TRACKERS_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/performance/viewEmployeePerformanceTrackerList";
    private static final String KPI_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/performance/searchKpi";
    private static final String PERFORMANCE_MODULE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/performance/viewPerformanceModule";

    public PerformancePage(WebDriver driver) {
        super(driver);
    }

    public void openManageReviews() {
        navigateTo(MANAGE_REVIEWS_URL);
    }

    public void openMyTrackers() {
        navigateTo(MY_TRACKERS_URL);
    }

    public void openEmployeeTrackers() {
        navigateTo(EMPLOYEE_TRACKERS_URL);
    }

    public void openKpiList() {
        navigateTo(KPI_URL);
    }

    public void openPerformanceModule() {
        navigateTo(PERFORMANCE_MODULE_URL);
    }

    public void assertManageReviewsHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertMyTrackersHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertEmployeeTrackersHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertKpiHeadingContains(String text) {
        assertPageBodyContains(text);
    }
}
