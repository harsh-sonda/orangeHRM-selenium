package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class CommonTest extends BaseTest {

    @Test(description = "Sidebar menu items")
    public void tc_common_001_sidebar_menu_items() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        common.assertSidebarVisible();
        common.assertSidebarContains("Admin");
        common.assertSidebarContains("PIM");
        common.assertSidebarContains("Buzz");
    }
    @Test(description = "User dropdown")
    public void tc_common_002_user_dropdown() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        common.openUserMenu();
        common.assertLogoutLinkVisible();
    }
    @Test(description = "Logout")
    public void tc_common_003_logout() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        common.openUserMenu();
        common.logout();
        new CommonPage(driver).assertUrlContains("auth/login");
    }
}
