package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PimPage extends BasePage {

    private static final String EMPLOYEE_LIST_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewEmployeeList";
    private static final String ADD_EMPLOYEE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/pim/addEmployee";
    private static final String PIM_MODULE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/pim/viewPimModule";

    private static final By EMPLOYEE_NAME_SEARCH = By.xpath(
            "(//div[contains(@class,'oxd-table-filter')]//input[@placeholder='Type for hints...'])[1]");
    private static final By EMPLOYEE_LIST_HEADING = By.cssSelector("h6.oxd-text--h6");
    private static final By ADD_EMPLOYEE_FIRSTNAME = By.name("firstName");
    private static final By ADD_EMPLOYEE_LASTNAME = By.name("lastName");

    public PimPage(WebDriver driver) {
        super(driver);
    }

    public void openEmployeeList() {
        navigateTo(EMPLOYEE_LIST_URL);
        assertPageBodyContains("Employee Information");
    }

    public void openAddEmployee() {
        navigateTo(ADD_EMPLOYEE_URL);
    }

    public void openPimModule() {
        navigateTo(PIM_MODULE_URL);
    }

    public void searchEmployeeName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".oxd-table-filter")));
        ensureTableFilterExpanded();
        type(EMPLOYEE_NAME_SEARCH, name);
        sleepQuietly(1000);
    }

    public void assertEmployeeListHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertAddEmployeeFormVisible() {
        assertVisible(ADD_EMPLOYEE_FIRSTNAME);
        assertVisible(ADD_EMPLOYEE_LASTNAME);
    }
}
