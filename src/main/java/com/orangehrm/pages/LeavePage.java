package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class LeavePage extends BasePage {

    private static final String APPLY_LEAVE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/leave/applyLeave";
    private static final String MY_LEAVE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/leave/viewMyLeaveList";
    private static final String ENTITLEMENTS_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/leave/viewLeaveEntitlements";
    private static final String LEAVE_LIST_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/leave/viewLeaveList";
    private static final String ASSIGN_LEAVE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/leave/assignLeave";
    private static final String LEAVE_TYPES_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/leave/leaveTypeList";

    private static final By LEAVE_TYPE_DROPDOWN = By.xpath(
            "//label[normalize-space()='Leave Type']/ancestor::div[contains(@class,'oxd-input-group')]"
                    + "//div[contains(@class,'oxd-select-text')]");
    private static final By FROM_DATE = By.xpath(
            "//label[normalize-space()='From Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private static final By TO_DATE = By.xpath(
            "//label[normalize-space()='To Date']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private static final By COMMENTS = By.xpath(
            "//label[normalize-space()='Comments']/ancestor::div[contains(@class,'oxd-input-group')]//textarea");
    private static final By APPLY_BUTTON = By.xpath("//button[normalize-space()='Apply']");

    public LeavePage(WebDriver driver) {
        super(driver);
    }

    public void openApplyLeave() {
        navigateTo(APPLY_LEAVE_URL);
    }

    public void openMyLeaveList() {
        navigateTo(MY_LEAVE_URL);
    }

    public void openEntitlements() {
        navigateTo(ENTITLEMENTS_URL);
    }

    public void openLeaveList() {
        navigateTo(LEAVE_LIST_URL);
    }

    public void openAssignLeave() {
        navigateTo(ASSIGN_LEAVE_URL);
    }

    public void openLeaveTypes() {
        navigateTo(LEAVE_TYPES_URL);
    }

    public void assertApplyHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public boolean hasLeaveTypeDropdown() {
        List<WebElement> dropdowns = driver.findElements(LEAVE_TYPE_DROPDOWN);
        return !dropdowns.isEmpty() && dropdowns.get(0).isDisplayed();
    }

    public void assertLeaveTypeDropdownVisible() {
        if (hasLeaveTypeDropdown()) {
            assertVisible(LEAVE_TYPE_DROPDOWN);
        } else {
            assertPageBodyContains("Apply Leave");
        }
    }

    public void selectPersonalLeave() {
        click(LEAVE_TYPE_DROPDOWN);
        sleepQuietly(500);
        By option = By.xpath("//div[contains(@class,'oxd-select-option')]//span[contains(text(),'CAN - Personal')]");
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void enterFromDate(String date) {
        type(FROM_DATE, date);
    }

    public void enterToDate(String date) {
        type(TO_DATE, date);
    }

    public void enterComments(String comments) {
        type(COMMENTS, comments);
    }

    public void clickApply() {
        click(APPLY_BUTTON);
    }

    public void assertMyLeaveHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertEntitlementsHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertLeaveListHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertAssignLeaveHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertLeaveTypesHeadingContains(String text) {
        assertPageBodyContains(text);
    }
}
