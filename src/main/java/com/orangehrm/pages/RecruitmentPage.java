package com.orangehrm.pages;

import com.orangehrm.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RecruitmentPage extends BasePage {

    private static final String CANDIDATES_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/viewCandidates";
    private static final String VACANCIES_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/viewJobVacancy";
    private static final String ADD_CANDIDATE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/addCandidate";
    private static final String ADD_VACANCY_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/addJobVacancy";
    private static final String RECRUITMENT_MODULE_URL =
            "https://opensource-demo.orangehrmlive.com/web/index.php/recruitment/viewRecruitmentModule";

    private static final By ADD_CANDIDATE_FIRSTNAME = By.name("firstName");
    private static final By ADD_VACANCY_NAME = By.xpath(
            "//label[normalize-space()='Vacancy Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    public RecruitmentPage(WebDriver driver) {
        super(driver);
    }

    public void openCandidates() {
        navigateTo(CANDIDATES_URL);
    }

    public void openVacancies() {
        navigateTo(VACANCIES_URL);
    }

    public void openAddCandidate() {
        navigateTo(ADD_CANDIDATE_URL);
    }

    public void openAddVacancy() {
        navigateTo(ADD_VACANCY_URL);
    }

    public void openRecruitmentModule() {
        navigateTo(RECRUITMENT_MODULE_URL);
    }

    public void assertCandidatesHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertVacanciesHeadingContains(String text) {
        assertPageBodyContains(text);
    }

    public void assertAddCandidateFormVisible() {
        assertVisible(ADD_CANDIDATE_FIRSTNAME);
    }

    public void assertAddVacancyFormVisible() {
        assertVisible(ADD_VACANCY_NAME);
    }
}
