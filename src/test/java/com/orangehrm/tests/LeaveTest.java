package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class LeaveTest extends BaseTest {

    @Test(description = "Apply Leave form")
    public void tc_leave_001_apply_leave_form() {
        loginAsAdmin();
        LeavePage leavePage = new LeavePage(driver);
        leavePage.openApplyLeave();
        leavePage.assertApplyHeadingContains("Leave");
        leavePage.assertLeaveTypeDropdownVisible();
    }
    @Test(description = "Apply Personal Leave")
    public void tc_leave_002_apply_personal_leave() {
        loginAsAdmin();
        LeavePage leavePage = new LeavePage(driver);
        CommonPage common = new CommonPage(driver);
        leavePage.openApplyLeave();
        if (leavePage.hasLeaveTypeDropdown()) {
            leavePage.selectPersonalLeave();
            leavePage.enterFromDate("2026-01-07");
            leavePage.enterToDate("2026-01-07");
            leavePage.enterComments("E2E automated leave test");
            leavePage.clickApply();
            sleepQuietly(2000);
            common.assertToastContains("Success");
        } else {
            leavePage.assertApplyHeadingContains("Apply Leave");
        }
    }
    @Test(description = "My Leave list")
    public void tc_leave_003_my_leave_list() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        LeavePage leavePage = new LeavePage(driver);
        leavePage.openMyLeaveList();
        new CommonPage(driver).assertUrlContains("viewMyLeaveList");
        leavePage.assertMyLeaveHeadingContains("Leave");
    }
    @Test(description = "Entitlements")
    public void tc_leave_004_entitlements() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        LeavePage leavePage = new LeavePage(driver);
        leavePage.openEntitlements();
        new CommonPage(driver).assertUrlContains("viewLeaveEntitlements");
        leavePage.assertEntitlementsHeadingContains("Entitlements");
    }
    @Test(description = "Leave List admin")
    public void tc_leave_005_leave_list_admin() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        LeavePage leavePage = new LeavePage(driver);
        leavePage.openLeaveList();
        new CommonPage(driver).assertUrlContains("viewLeaveList");
        leavePage.assertLeaveListHeadingContains("Leave List");
    }
    @Test(description = "Assign Leave")
    public void tc_leave_006_assign_leave() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        LeavePage leavePage = new LeavePage(driver);
        leavePage.openAssignLeave();
        new CommonPage(driver).assertUrlContains("assignLeave");
        leavePage.assertAssignLeaveHeadingContains("Assign Leave");
    }
    @Test(description = "Leave List report")
    public void tc_leave_007_leave_list_report() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        LeavePage leavePage = new LeavePage(driver);
        leavePage.openLeaveList();
        new CommonPage(driver).assertUrlContains("viewLeaveList");
        leavePage.assertLeaveListHeadingContains("Leave");
    }
    @Test(description = "Leave Types config")
    public void tc_leave_008_leave_types_config() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        LeavePage leavePage = new LeavePage(driver);
        leavePage.openLeaveTypes();
        new CommonPage(driver).assertUrlContains("leaveTypeList");
        leavePage.assertLeaveTypesHeadingContains("Leave Types");
    }
}
