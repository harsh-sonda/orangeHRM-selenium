package com.orangehrm.base;

import com.orangehrm.config.TestConfig;
import com.orangehrm.listeners.RealTimeTestListener;
import com.orangehrm.pages.DashboardPage;
import com.orangehrm.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.time.Duration;

@Listeners(RealTimeTestListener.class)
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        if (TestConfig.isHeadless()) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--disable-extensions");
        }
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestConfig.getImplicitWaitSeconds()));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void loginAsAdmin() {
        RuntimeException lastFailure = null;
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                LoginPage loginPage = new LoginPage(driver);
                loginPage.open();
                loginPage.login(TestConfig.getAdminUsername(), TestConfig.getAdminPassword());
                loginPage.waitForLoginResult();
                new DashboardPage(driver).waitForDashboard();
                return;
            } catch (RuntimeException e) {
                lastFailure = e;
                if (attempt < 3) {
                    BasePage.sleepQuietly(2000L * attempt);
                }
            }
        }
        throw lastFailure;
    }
}
