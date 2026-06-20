package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.WebDriver;

public class MaintenancePage extends BasePage {

    private static final String PURGE_EMPLOYEE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/maintenance/purgeEmployee";
    private static final String MAINTENANCE_MODULE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/maintenance/viewMaintenanceModule";

    public MaintenancePage(WebDriver driver) {
        super(driver);
    }

    public void openPurgeEmployee() {
        navigateTo(PURGE_EMPLOYEE_URL);
    }

    public void openMaintenanceModule() {
        navigateTo(MAINTENANCE_MODULE_URL);
    }

    public void assertPurgeEmployeeHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertAccessRecordsHeadingContains(String text) {
        assertPageBodyContains(text);
    }
}
