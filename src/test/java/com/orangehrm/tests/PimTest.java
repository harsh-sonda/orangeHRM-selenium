package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class PimTest extends BaseTest {

    @Test(description = "Employee list loads")
    public void tc_pim_001_employee_list_loads() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PimPage pimPage = new PimPage(driver);
        pimPage.openEmployeeList();
        pimPage.assertEmployeeListHeadingContains("PIM");
        common.assertTableVisible();
    }
    @Test(description = "Search employee Peter")
    public void tc_pim_002_search_employee_peter() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PimPage pimPage = new PimPage(driver);
        pimPage.openEmployeeList();
        pimPage.searchEmployeeName("Peter");
        common.clickSearch();
        sleepQuietly(2000);
        common.assertTableContains("Peter");
    }
    @Test(description = "Reset employee search")
    public void tc_pim_003_reset_employee_search() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PimPage pimPage = new PimPage(driver);
        pimPage.openEmployeeList();
        pimPage.searchEmployeeName("Peter");
        common.clickSearch();
        common.clickReset();
        common.assertTableVisible();
    }
    @Test(description = "Add Employee form")
    public void tc_pim_004_add_employee_form() {
        loginAsAdmin();
        PimPage pimPage = new PimPage(driver);
        pimPage.openAddEmployee();
        pimPage.assertAddEmployeeFormVisible();
    }
    @Test(description = "Employee List report")
    public void tc_pim_005_employee_list_report() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PimPage pimPage = new PimPage(driver);
        pimPage.openEmployeeList();
        new CommonPage(driver).assertUrlContains("viewEmployeeList");
        pimPage.assertEmployeeListHeadingContains("PIM");
    }
    @Test(description = "Configuration fields")
    public void tc_pim_006_configuration_fields() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        PimPage pimPage = new PimPage(driver);
        pimPage.openPimModule();
        new CommonPage(driver).assertUrlContainsAny("viewEmployeeList", "viewPimModule");
        common.assertPageHeadingContains("PIM");
    }
}
