package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class DirectoryTest extends BaseTest {

    @Test(description = "Directory grid")
    public void tc_dir_001_directory_grid() {
        loginAsAdmin();
        DirectoryPage directoryPage = new DirectoryPage(driver);
        directoryPage.openDirectory();
        directoryPage.assertDirectoryHeadingContains("Directory");
        directoryPage.assertDirectoryCardsVisible();
    }
    @Test(description = "Directory search")
    public void tc_dir_002_directory_search() {
        loginAsAdmin();
        DirectoryPage directoryPage = new DirectoryPage(driver);
        directoryPage.openDirectory();
        directoryPage.searchDirectory("Peter");
        sleepQuietly(2000);
        directoryPage.assertSearchResultsContain("Peter");
    }
}
