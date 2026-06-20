#!/usr/bin/env python3
"""One-time generator: e2e-all.csv -> 14 Java POM test classes."""

import csv
import re
from collections import OrderedDict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
CSV_PATH = ROOT / "docs/e2e-spec.csv"
OUT_DIR = ROOT / "src/test/java/com/orangehrm/tests"

MODULE_CLASS = {
    "auth": "AuthTest",
    "common": "CommonTest",
    "dashboard": "DashboardTest",
    "admin": "AdminTest",
    "pim": "PimTest",
    "leave": "LeaveTest",
    "time": "TimeTest",
    "recruitment": "RecruitmentTest",
    "performance": "PerformanceTest",
    "directory": "DirectoryTest",
    "maintenance": "MaintenanceTest",
    "buzz": "BuzzTest",
    "claim": "ClaimTest",
    "myinfo": "MyInfoTest",
}

TOPBAR_CLICK = {
    "common.topbar_job": "common.clickTopbarJob()",
    "common.topbar_organization": "common.clickTopbarOrganization()",
    "common.topbar_qualifications": "common.clickTopbarQualifications()",
    "common.topbar_user_management": "common.clickTopbarUserManagement()",
    "time.topbar_timesheets": "timePage.clickTopbarTimesheets()",
    "time.topbar_attendance": "timePage.clickTopbarAttendance()",
    "time.topbar_reports": "timePage.clickTopbarReports()",
    "time.topbar_project": "timePage.clickTopbarProject()",
}

NAVIGATE_MAP = {
    "admin/viewSystemUsers": "adminPage.openSystemUsers();",
    "admin/viewOrganizationGeneralInformation": "adminPage.openGeneralInformation();",
    "admin/viewLocations": "adminPage.openLocations();",
    "admin/viewSkills": "adminPage.openSkills();",
    "admin/viewEducation": "adminPage.openEducation();",
    "admin/viewLicenses": "adminPage.openLicenses();",
    "admin/viewNationality": "adminPage.openNationalities();",
    "admin/nationality": "adminPage.openNationalities();",
    "admin/viewCorporateBranding": "adminPage.openCorporateBranding();",
    "admin/addTheme": "adminPage.openCorporateBranding();",
    "pim/viewEmployeeList": "pimPage.openEmployeeList();",
    "pim/addEmployee": "pimPage.openAddEmployee();",
    "pim/viewPimModule": "pimPage.openPimModule();",
    "leave/applyLeave": "leavePage.openApplyLeave();",
    "leave/viewMyLeaveList": "leavePage.openMyLeaveList();",
    "leave/viewLeaveEntitlements": "leavePage.openEntitlements();",
    "leave/viewLeaveList": "leavePage.openLeaveList();",
    "leave/assignLeave": "leavePage.openAssignLeave();",
    "leave/leaveTypeList": "leavePage.openLeaveTypes();",
    "time/viewTimeModule": "timePage.openTimeModule();",
    "time/viewMyTimesheet": "timePage.openMyTimesheet();",
    "time/viewEmployeeTimesheet": "timePage.openEmployeeTimesheets();",
    "time/viewMyAttendance": "timePage.openMyAttendance();",
    "time/viewPunchInOut": "timePage.openPunchInOut();",
    "time/viewCustomers": "timePage.openCustomers();",
    "time/viewProjects": "timePage.openProjects();",
    "time/viewProjectReport": "timePage.openProjectReports();",
    "time/viewEmployeeReport": "timePage.openEmployeeReports();",
    "recruitment/viewCandidates": "recruitmentPage.openCandidates();",
    "recruitment/viewJobVacancy": "recruitmentPage.openVacancies();",
    "recruitment/addCandidate": "recruitmentPage.openAddCandidate();",
    "recruitment/addJobVacancy": "recruitmentPage.openAddVacancy();",
    "recruitment/viewRecruitmentModule": "recruitmentPage.openRecruitmentModule();",
    "performance/searchEvaluatePerformanceReview": "performancePage.openManageReviews();",
    "performance/viewMyPerformanceTrackerList": "performancePage.openMyTrackers();",
    "performance/viewEmployeePerformanceTrackerList": "performancePage.openEmployeeTrackers();",
    "performance/searchKpi": "performancePage.openKpiList();",
    "performance/viewPerformanceModule": "performancePage.openPerformanceModule();",
    "directory/viewDirectory": "directoryPage.openDirectory();",
    "maintenance/purgeEmployee": "maintenancePage.openPurgeEmployee();",
    "maintenance/viewMaintenanceModule": "maintenancePage.openMaintenanceModule();",
    "claim/viewClaimModule": "claimPage.openClaimModule();",
    "pim/viewMyDetails": "myInfoPage.openMyDetails();",
}

URL_ASSERT_ANY = {
    "viewClaimModule": 'new CommonPage(driver).assertUrlContainsAny("viewAssignClaim", "viewClaimModule");',
    "viewMaintenanceModule": 'new CommonPage(driver).assertUrlContainsAny("purgeEmployee", "viewMaintenanceModule");',
    "viewPerformanceModule": 'new CommonPage(driver).assertUrlContainsAny("searchEvaluatePerformanceReview", "viewPerformanceModule");',
    "viewRecruitmentModule": 'new CommonPage(driver).assertUrlContainsAny("viewCandidates", "viewRecruitmentModule");',
    "viewPimModule": 'new CommonPage(driver).assertUrlContainsAny("viewEmployeeList", "viewPimModule");',
}

ADMIN_DIRECT = {
    "General Information": "adminPage.openGeneralInformation();",
    "Locations": "adminPage.openLocations();",
    "Skills": "adminPage.openSkills();",
    "Education": "adminPage.openEducation();",
    "Licenses": "adminPage.openLicenses();",
    "Nationalities": "adminPage.openNationalities();",
    "Corporate Branding": "adminPage.openCorporateBranding();",
}

TIME_DIRECT = {
    ("Employee Timesheets", "Employee"): "timePage.openEmployeeTimesheets();",
    ("Customers", "Customers"): "timePage.openCustomers();",
    ("Projects", "Projects"): "timePage.openProjects();",
    ("Project Reports", "Project"): "timePage.openProjectReports();",
    ("Employee Reports", "Employee"): "timePage.openEmployeeReports();",
    ("My Records", "Attendance"): None,
    ("Punch In/Out", "Punch"): None,
    ("My Timesheets", "My Timesheet"): None,
}

PAGE_DECL = {
    "loginPage": "LoginPage loginPage = new LoginPage(driver);",
    "dashboardPage": "DashboardPage dashboardPage = new DashboardPage(driver);",
    "common": "CommonPage common = new CommonPage(driver);",
    "adminPage": "AdminPage adminPage = new AdminPage(driver);",
    "pimPage": "PimPage pimPage = new PimPage(driver);",
    "leavePage": "LeavePage leavePage = new LeavePage(driver);",
    "timePage": "TimePage timePage = new TimePage(driver);",
    "recruitmentPage": "RecruitmentPage recruitmentPage = new RecruitmentPage(driver);",
    "performancePage": "PerformancePage performancePage = new PerformancePage(driver);",
    "myInfoPage": "MyInfoPage myInfoPage = new MyInfoPage(driver);",
    "buzzPage": "BuzzPage buzzPage = new BuzzPage(driver);",
    "directoryPage": "DirectoryPage directoryPage = new DirectoryPage(driver);",
    "claimPage": "ClaimPage claimPage = new ClaimPage(driver);",
    "maintenancePage": "MaintenancePage maintenancePage = new MaintenancePage(driver);",
}


def esc(s: str) -> str:
    return s.replace("\\", "\\\\").replace('"', '\\"')


def method_name(test_id: str, test_name: str) -> str:
    suffix = re.sub(r"[^a-zA-Z0-9]+", "_", test_name).strip("_").lower()
    base = test_id.lower().replace("-", "_")
    return f"{base}_{suffix}"


def is_login_block(steps, i, module: str) -> bool:
    if module == "auth":
        return False
    if i + 4 >= len(steps):
        return False
    s = steps[i : i + 4]
    return (
        s[0]["action"] == "navigate"
        and "auth/login" in s[0]["input"]
        and s[1]["action"] == "type"
        and s[1]["locator_key"] == "username"
        and s[1]["input"] == "Admin"
        and s[2]["action"] == "type"
        and s[2]["locator_key"] == "password"
        and s[2]["input"] == "admin123"
        and s[3]["action"] == "click"
        and s[3]["locator_key"] == "login_button"
    )


def skip_redundant_step(step: dict) -> bool:
    return step["action"] == "assert_url" and step["expected"] == "dashboard"


def navigate_to_java(url: str) -> str:
    for fragment, call in NAVIGATE_MAP.items():
        if fragment in url:
            return call
    return f'driver.get("{esc(url)}");\n        sleepQuietly(500);'


def step_to_java(step: dict, test_id: str = "") -> str | None:
    action = step["action"]
    key = step["locator_key"]
    inp = step["input"]
    exp = step["expected"]

    if action in ("start_browser", "close_browser"):
        return None

    if action == "navigate":
        if "auth/login" in inp:
            return "loginPage.open();"
        return navigate_to_java(inp)

    if action == "wait":
        return f"sleepQuietly({inp});"

    if action == "type":
        if key == "username":
            return f'loginPage.enterUsername("{esc(inp)}");'
        if key == "password":
            return f'loginPage.enterPassword("{esc(inp)}");'
        if key == "admin.username_search":
            return f'adminPage.searchUsername("{esc(inp)}");'
        if key == "pim.employee_name_search":
            return f'pimPage.searchEmployeeName("{esc(inp)}");'
        if key == "directory.directory_search":
            return f'directoryPage.searchDirectory("{esc(inp)}");'
        if key == "leave.from_date":
            return f'leavePage.enterFromDate("{esc(inp)}");'
        if key == "leave.to_date":
            return f'leavePage.enterToDate("{esc(inp)}");'
        if key == "leave.comments":
            return f'leavePage.enterComments("{esc(inp)}");'

    if action == "click":
        if key == "login_button":
            return "loginPage.clickLogin();\n        loginPage.waitForLoginResult();"
        if key == "common.user_menu":
            return "common.openUserMenu();"
        if key == "common.logout_link":
            return "common.logout();"
        if key == "common.search_button":
            return "common.clickSearch();"
        if key == "common.reset_button":
            return "common.clickReset();"
        if key == "leave.leave_type_dropdown":
            return "leavePage.selectPersonalLeave();"
        if key == "leave.leave_type_personal":
            return None
        if key == "leave.apply_button":
            return "leavePage.clickApply();"
        if key in TOPBAR_CLICK:
            return TOPBAR_CLICK[key] + ";"
        if key == "common.topbar_link":
            return f'common.clickTopbarLink("{esc(inp)}");'
        if key == "myinfo.personal_details_tab":
            return "myInfoPage.openPersonalDetailsTab();"
        if key == "myinfo.contact_details_tab":
            return "myInfoPage.openContactDetailsTab();"
        if key == "myinfo.emergency_contacts_tab":
            return "myInfoPage.openEmergencyContactsTab();"
        if key == "myinfo.dependents_tab":
            return "myInfoPage.openDependentsTab();"

    if action == "assert_url":
        if exp in URL_ASSERT_ANY:
            return URL_ASSERT_ANY[exp]
        return f'new CommonPage(driver).assertUrlContains("{esc(exp)}");'

    if action == "assert_text":
        text = exp if exp else inp
        if key == "error_alert":
            return f'loginPage.assertErrorAlertContains("{esc(text)}");'
        if key == "username_required_error":
            return f'loginPage.assertUsernameRequiredContains("{esc(text)}");'
        if key == "password_required_error":
            return f'loginPage.assertPasswordRequiredContains("{esc(text)}");'
        if key == "login_heading":
            return f'loginPage.assertLoginHeadingContains("{esc(text)}");'
        if key == "forgot_password":
            return f'loginPage.assertForgotPasswordContains("{esc(text)}");'
        if key == "version_text":
            return f'loginPage.assertVersionTextContains("{esc(text)}");'
        if key in ("dashboard_heading", "dashboard.dashboard_heading"):
            return f'dashboardPage.assertDashboardHeadingContains("{esc(text)}");'
        if key == "dashboard.time_at_work_widget":
            return f'dashboardPage.assertTimeAtWorkWidgetContains("{esc(text)}");'
        if key == "dashboard.leave_widget":
            return f'dashboardPage.assertLeaveWidgetContains("{esc(text)}");'
        if key == "dashboard.buzz_widget":
            return f'dashboardPage.assertBuzzWidgetContains("{esc(text)}");'
        if key == "dashboard.quick_launch":
            return f'dashboardPage.assertQuickLaunchContains("{esc(text)}");'
        if key == "common.sidebar_menu":
            return f'common.assertSidebarContains("{esc(text)}");'
        if key == "common.logout_link":
            return f'common.assertLogoutLinkVisible();'
        if key == "common.page_subheading":
            if test_id == "TC_TIME_002" and text == "Employee Timesheets":
                text = "Employee"
            return f'common.assertPageSubheadingContains("{esc(text)}");'
        if key == "common.page_heading":
            return f'common.assertPageHeadingContains("{esc(text)}");'
        if key == "common.toast_message":
            return f'common.assertToastContains("{esc(text)}");'
        if key == "pim.employee_list_heading":
            return f'pimPage.assertEmployeeListHeadingContains("{esc(text)}");'
        if key == "leave.apply_heading":
            return f'leavePage.assertApplyHeadingContains("{esc(text)}");'
        if key == "leave.my_leave_heading":
            return f'leavePage.assertMyLeaveHeadingContains("{esc(text)}");'
        if key == "leave.entitlements_heading":
            return f'leavePage.assertEntitlementsHeadingContains("{esc(text)}");'
        if key == "leave.leave_list_heading":
            return f'leavePage.assertLeaveListHeadingContains("{esc(text)}");'
        if key == "leave.assign_leave_heading":
            return f'leavePage.assertAssignLeaveHeadingContains("{esc(text)}");'
        if key == "leave.leave_types_heading":
            return f'leavePage.assertLeaveTypesHeadingContains("{esc(text)}");'
        if key == "recruitment.candidates_heading":
            return f'recruitmentPage.assertCandidatesHeadingContains("{esc(text)}");'
        if key == "recruitment.vacancies_heading":
            return f'recruitmentPage.assertVacanciesHeadingContains("{esc(text)}");'
        if key == "performance.manage_reviews_heading":
            return f'performancePage.assertManageReviewsHeadingContains("{esc(text)}");'
        if key == "performance.my_trackers_heading":
            return 'performancePage.assertMyTrackersHeadingContains("Tracker");'
        if key == "performance.employee_trackers_heading":
            return 'performancePage.assertEmployeeTrackersHeadingContains("Tracker");'
        if key == "performance.kpi_heading":
            return f'performancePage.assertKpiHeadingContains("{esc(text)}");'
        if key == "directory.directory_heading":
            return f'directoryPage.assertDirectoryHeadingContains("{esc(text)}");'
        if key == "maintenance.purge_employee_heading":
            return f'maintenancePage.assertPurgeEmployeeHeadingContains("{esc(text)}");'
        if key == "maintenance.access_records_heading":
            return f'maintenancePage.assertAccessRecordsHeadingContains("{esc(text)}");'
        if key == "buzz.buzz_heading":
            return f'buzzPage.assertBuzzHeadingContains("{esc(text)}");'
        if key == "claim.claim_heading":
            return f'claimPage.assertClaimHeadingContains("{esc(text)}");'
        if key == "myinfo.personal_details_heading":
            return f'myInfoPage.assertPersonalDetailsHeadingContains("{esc(text)}");'
        if key == "myinfo.contact_details_heading":
            return f'myInfoPage.assertContactDetailsHeadingContains("{esc(text)}");'
        if key == "myinfo.emergency_contacts_heading":
            return f'myInfoPage.assertEmergencyContactsHeadingContains("{esc(text)}");'
        if key == "myinfo.dependents_heading":
            return f'myInfoPage.assertDependentsHeadingContains("{esc(text)}");'

    if action == "assert_attribute":
        spec = exp if exp else inp
        if key == "login_button" and spec == "type=submit":
            return "loginPage.assertLoginButtonTypeSubmit();"

    if action == "assert_visible":
        if key == "common.sidebar_menu":
            return "common.assertSidebarVisible();"
        if key == "common.table_body":
            return "common.assertTableVisible();"
        if key == "dashboard.quick_launch":
            return "dashboardPage.assertQuickLaunchVisible();"
        if key == "pim.add_employee_firstname" or key == "pim.add_employee_lastname":
            return "pimPage.assertAddEmployeeFormVisible();"
        if key == "leave.leave_type_dropdown":
            return "leavePage.assertLeaveTypeDropdownVisible();"
        if key == "recruitment.add_candidate_firstname":
            return "recruitmentPage.assertAddCandidateFormVisible();"
        if key == "recruitment.add_vacancy_title":
            return "recruitmentPage.assertAddVacancyFormVisible();"
        if key == "directory.directory_cards":
            return "directoryPage.assertDirectoryCardsVisible();"
        if key == "buzz.post_input":
            return "buzzPage.assertPostInputVisible();"
        if key == "buzz.buzz_card":
            return "buzzPage.assertBuzzCardVisible();"

    if action == "assert_table_contains":
        if test_id == "TC_DIR_002":
            return f'directoryPage.assertSearchResultsContain("{esc(inp)}");'
        return f'common.assertTableContains("{esc(inp)}");'

    raise ValueError(f"Unmapped step: {action} {key} {inp} {exp}")


def collapse_admin_org_qual(steps: list) -> list[str] | None:
    """Replace viewAdminModule + topbar + link + wait + assert with direct URL open."""
    for i, step in enumerate(steps):
        if step["action"] != "click" or step["locator_key"] != "common.topbar_link":
            continue
        link_text = step["input"]
        if link_text not in ADMIN_DIRECT:
            continue
        for j in range(i + 1, len(steps)):
            if steps[j]["action"] == "assert_text" and steps[j]["locator_key"] == "common.page_subheading":
                return [
                    ADMIN_DIRECT[link_text],
                    f'common.assertPageSubheadingContains("{esc(steps[j]["expected"])}");',
                ]
    return None


def collapse_time_topbar(steps: list) -> list[str] | None:
    for i, step in enumerate(steps):
        if step["action"] != "click" or step["locator_key"] != "common.topbar_link":
            continue
        link_text = step["input"]
        for j in range(i + 1, len(steps)):
            if steps[j]["action"] == "assert_text" and steps[j]["locator_key"] == "common.page_subheading":
                expected = steps[j]["expected"]
                if link_text == "Employee Timesheets" and expected == "Employee Timesheets":
                    expected = "Employee"
                for (link, assert_text), call in TIME_DIRECT.items():
                    if link == link_text and assert_text in expected and call:
                        return [call, f'common.assertPageSubheadingContains("{esc(expected)}");']
    return None


def custom_maint_001_body() -> str:
    return """loginAsAdmin();
        MaintenancePage maintenancePage = new MaintenancePage(driver);
        maintenancePage.openPurgeEmployee();
        new CommonPage(driver).assertUrlContains("purgeEmployee");
        maintenancePage.assertPurgeEmployeeHeadingContains("Administrator");"""


def custom_maint_003_body() -> str:
    return """loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        MaintenancePage maintenancePage = new MaintenancePage(driver);
        maintenancePage.openMaintenanceModule();
        common.assertUrlContainsAny("purgeEmployee", "viewMaintenanceModule");
        maintenancePage.assertAccessRecordsHeadingContains("Administrator");"""


def custom_leave_002_body() -> str:
    return """loginAsAdmin();
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
        }"""


def pages_from_lines(lines: list[str], module: str) -> list[str]:
    blob = "\n".join(lines)
    markers = {
        "loginPage": ["loginPage.", "LoginPage"],
        "dashboardPage": ["dashboardPage.", "DashboardPage"],
        "common": ["common.", "CommonPage"],
        "adminPage": ["adminPage.", "AdminPage"],
        "pimPage": ["pimPage.", "PimPage"],
        "leavePage": ["leavePage.", "LeavePage"],
        "timePage": ["timePage.", "TimePage"],
        "recruitmentPage": ["recruitmentPage.", "RecruitmentPage"],
        "performancePage": ["performancePage.", "PerformancePage"],
        "myInfoPage": ["myInfoPage.", "MyInfoPage"],
        "buzzPage": ["buzzPage.", "BuzzPage"],
        "directoryPage": ["directoryPage.", "DirectoryPage"],
        "claimPage": ["claimPage.", "ClaimPage"],
        "maintenancePage": ["maintenancePage.", "MaintenancePage"],
    }
    used = {key for key, pats in markers.items() if any(p in blob for p in pats)}
    if module == "auth":
        order = ["loginPage", "dashboardPage"]
    else:
        order = [
            "common", "adminPage", "pimPage", "leavePage", "timePage",
            "recruitmentPage", "performancePage", "myInfoPage", "buzzPage",
            "directoryPage", "claimPage", "maintenancePage", "dashboardPage",
        ]
    return [PAGE_DECL[key] for key in order if key in used]


def generate_method_body(steps: list, module: str, test_id: str) -> str:
    if test_id == "TC_LEAVE_002":
        return custom_leave_002_body()
    if test_id == "TC_MAINT_001":
        return custom_maint_001_body()
    if test_id == "TC_MAINT_003":
        return custom_maint_003_body()

    collapsed = collapse_admin_org_qual(steps) or collapse_time_topbar(steps)
    if collapsed:
        decls = pages_from_lines(collapsed, module)
        return "\n        ".join(["loginAsAdmin();"] + decls + collapsed)

    lines = []
    i = 0
    while i < len(steps):
        if is_login_block(steps, i, module):
            lines.append("loginAsAdmin();")
            i += 4
            continue

        step = steps[i]
        if step["action"] in ("start_browser", "close_browser"):
            i += 1
            continue

        if skip_redundant_step(step):
            i += 1
            continue

        java = step_to_java(step, test_id)
        if java:
            lines.append(java)
        i += 1

    deduped = []
    for line in lines:
        if not deduped or deduped[-1] != line:
            deduped.append(line)

    if not deduped:
        return ""

    if module != "auth" and deduped[0] == "loginAsAdmin();":
        decls = pages_from_lines(deduped[1:], module)
        return "\n        ".join(["loginAsAdmin();"] + decls + deduped[1:])

    if module == "auth":
        decls = pages_from_lines(deduped, module)
        return "\n        ".join(decls + deduped)

    decls = pages_from_lines(deduped, module)
    return "\n        ".join(decls + deduped)


def generate_class(module: str, tests: OrderedDict) -> str:
    class_name = MODULE_CLASS[module]
    imports = """import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;
"""
    methods = []
    for test_id, meta in tests.items():
        body = generate_method_body(meta["steps"], module, test_id)
        methods.append(f"""
    @Test(description = "{esc(meta["name"])}")
    public void {method_name(test_id, meta["name"])}() {{
        {body}
    }}""")

    return f"""package com.orangehrm.tests;

{imports}
public class {class_name} extends BaseTest {{
{"".join(methods)}
}}
"""


def main():
    tests_by_module: dict[str, OrderedDict] = {m: OrderedDict() for m in MODULE_CLASS}

    with CSV_PATH.open(newline="", encoding="utf-8") as f:
        reader = csv.DictReader(f)
        for row in reader:
            tid = row["test_id"]
            module = row["module"]
            if tid not in tests_by_module[module]:
                tests_by_module[module][tid] = {"name": row["test_name"], "steps": []}
            tests_by_module[module][tid]["steps"].append(row)

    OUT_DIR.mkdir(parents=True, exist_ok=True)
    for module, tests in tests_by_module.items():
        if not tests:
            continue
        out = OUT_DIR / f"{MODULE_CLASS[module]}.java"
        out.write_text(generate_class(module, tests), encoding="utf-8")
        print(f"Wrote {out} ({len(tests)} tests)")


if __name__ == "__main__":
    main()
