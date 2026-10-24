package com.omnibank.automation.stepdefinitions;

import com.omnibank.automation.base.DriverFactory;
import com.omnibank.automation.base.DriverManager;
import com.omnibank.automation.utilities.ConfigReader;
import com.omnibank.automation.utilities.Constants;
import com.omnibank.automation.utilities.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.time.Duration;

/**
 * Cucumber's equivalent of TestNG's BaseTest @BeforeMethod/@AfterMethod.
 * @Before runs before EVERY scenario, @After runs after EVERY scenario -
 * regardless of which .feature file or step definition class is involved.
 */
public class Hooks {

    private static final Logger logger = LogManager.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        String browser = System.getProperty("browser", ConfigReader.get("browser"));
        WebDriver driver = DriverFactory.createDriver(browser);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Constants.IMPLICIT_WAIT));
        driver.get(ConfigReader.get("url"));

        DriverManager.setDriver(driver);
        logger.info("Browser [" + browser + "] launched for scenario: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (scenario.isFailed() && driver != null) {
            // 1. Attach screenshot directly into the Cucumber/Extent report (inline, visible immediately)
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshotBytes, "image/png", scenario.getName());

            // 2. ALSO save a copy to disk under /screenshots/ using the existing utility,
            //    so failures are browsable as standalone files too, not just inside the report
            ScreenshotUtils.captureScreenshot(driver, scenario.getName());

            logger.error("SCENARIO FAILED: " + scenario.getName());
        } else {
            logger.info("SCENARIO PASSED: " + scenario.getName());
        }

        DriverManager.quitDriver();
    }
}
