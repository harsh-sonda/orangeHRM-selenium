package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class MaintenanceTest extends BaseTest {

    @Test(description = "Purge Employee")
    public void tc_maint_001_purge_employee() {
        loginAsAdmin();
        MaintenancePage maintenancePage = new MaintenancePage(driver);
        maintenancePage.openPurgeEmployee();
        new CommonPage(driver).assertUrlContains("purgeEmployee");
        maintenancePage.assertPurgeEmployeeHeadingContains("Administrator");
    }
    @Test(description = "Maintenance module")
    public void tc_maint_002_maintenance_module() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        MaintenancePage maintenancePage = new MaintenancePage(driver);
        maintenancePage.openMaintenanceModule();
        new CommonPage(driver).assertUrlContainsAny("purgeEmployee", "viewMaintenanceModule");
        maintenancePage.assertAccessRecordsHeadingContains("Administrator");
    }
    @Test(description = "Maintenance access")
    public void tc_maint_003_maintenance_access() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        MaintenancePage maintenancePage = new MaintenancePage(driver);
        maintenancePage.openMaintenanceModule();
        common.assertUrlContainsAny("purgeEmployee", "viewMaintenanceModule");
        maintenancePage.assertAccessRecordsHeadingContains("Administrator");
    }
}
