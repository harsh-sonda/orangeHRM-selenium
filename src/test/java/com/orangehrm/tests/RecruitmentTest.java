package com.orangehrm.tests;

import com.orangehrm.base.BaseTest;
import com.orangehrm.pages.*;
import org.testng.annotations.Test;

import static com.orangehrm.base.BasePage.sleepQuietly;

public class RecruitmentTest extends BaseTest {

    @Test(description = "Candidates list")
    public void tc_rec_001_candidates_list() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        recruitmentPage.openCandidates();
        new CommonPage(driver).assertUrlContains("viewCandidates");
        recruitmentPage.assertCandidatesHeadingContains("Candidates");
    }
    @Test(description = "Vacancies list")
    public void tc_rec_002_vacancies_list() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        recruitmentPage.openVacancies();
        new CommonPage(driver).assertUrlContains("viewJobVacancy");
        recruitmentPage.assertVacanciesHeadingContains("Vacancies");
    }
    @Test(description = "Add Candidate form")
    public void tc_rec_003_add_candidate_form() {
        loginAsAdmin();
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        recruitmentPage.openAddCandidate();
        recruitmentPage.assertAddCandidateFormVisible();
    }
    @Test(description = "Add Vacancy form")
    public void tc_rec_004_add_vacancy_form() {
        loginAsAdmin();
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        recruitmentPage.openAddVacancy();
        recruitmentPage.assertAddVacancyFormVisible();
    }
    @Test(description = "Recruitment module")
    public void tc_rec_005_recruitment_module() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        recruitmentPage.openRecruitmentModule();
        new CommonPage(driver).assertUrlContainsAny("viewCandidates", "viewRecruitmentModule");
        common.assertPageHeadingContains("Recruitment");
    }
    @Test(description = "Candidates report entry")
    public void tc_rec_006_candidates_report_entry() {
        loginAsAdmin();
        CommonPage common = new CommonPage(driver);
        RecruitmentPage recruitmentPage = new RecruitmentPage(driver);
        recruitmentPage.openCandidates();
        new CommonPage(driver).assertUrlContains("viewCandidates");
        recruitmentPage.assertCandidatesHeadingContains("Candidates");
    }
}
