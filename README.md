# OmniBank BDD Automation Framework

## Overview
Selenium + Java + TestNG + Cucumber (BDD) automation framework for a web banking
application, covering Login and Dashboard modules using Gherkin feature files.

## Tech Stack
- Selenium 4.21
- Cucumber 7.18 (cucumber-java, cucumber-testng)
- TestNG 7.10 (runs Cucumber scenarios via AbstractTestNGCucumberTests)
- Extent Reports 5.1 + extentreports-cucumber7-adapter (auto-generated per-scenario HTML report)
- Log4j2 (logging)
- WebDriverManager (auto driver binaries)
- Maven (build tool)
- Jenkins (CI/CD)

## How This Differs From a Plain TestNG Framework
- Test data lives in Gherkin `Scenario Outline` + `Examples` tables (see Login.feature)
  instead of Excel/DataProvider - this is the BDD-idiomatic way to do data-driven testing.
- There's no BaseTest class. Cucumber's `Hooks.java` (`@Before`/`@After`) replaces
  TestNG's `@BeforeMethod`/`@AfterMethod`.
- Because Cucumber creates Hooks and Step Definition classes as separate objects for
  the same scenario, `DriverManager` (a ThreadLocal-based holder) is used to share the
  WebDriver instance between them - this also makes parallel scenario execution thread-safe.
- Screenshot-on-failure is done inside `Hooks.java`'s `@After` method, using Cucumber's
  `scenario.attach()` (embeds the image directly into the report) AND the existing
  `ScreenshotUtils` (saves a standalone copy to disk).
- `RetryAnalyzer`/`RetryTransformer` are reused as-is from the TestNG version - since
  `AbstractTestNGCucumberTests` runs every Gherkin scenario as an underlying TestNG
  `@Test` method, the same retry logic applies automatically to failed scenarios.
- Cucumber's own `rerun:` plugin writes failed scenarios to `reports/failed_scenarios.txt`,
  which `RerunFailedTests.java` reads to re-run ONLY the failed ones as a second pass -
  this is separate from (and complementary to) the in-run RetryAnalyzer.

## How to Import Into IntelliJ
1. Open IntelliJ -> File -> Open -> select this project's root folder (containing pom.xml)
2. Load/Import the Maven project when prompted (or Maven tool window -> Reload All Maven Projects)
3. Install the Cucumber for Java plugin if not already enabled:
   File -> Settings -> Plugins -> search "Cucumber for Java" -> install/enable
   (this gives step-to-definition navigation and gutter icons to run scenarios directly)

## How to Run

Full suite (all feature files):
```
mvn clean test
```

With a specific environment/browser:
```
mvn clean test -Denv=qa -Dbrowser=chrome
mvn clean test -Denv=staging -Dbrowser=firefox
```

Rerun only failed scenarios from the last run:
```
mvn test -Dtest=RerunFailedTests
```

Run only a tag (e.g. just smoke tests):
Edit `tags = "@regression or @smoke"` in TestRunner.java, or pass via:
```
mvn clean test -Dcucumber.filter.tags="@smoke"
```

## Reports & Logs (auto-generated after a run)
- Extent Report (Cucumber adapter): /reports/CucumberExtentReport.html
- Cucumber's own HTML report: /reports/cucumber-html-report.html
- Cucumber JSON (for CI tooling): /reports/cucumber.json
- Failed scenarios list (for rerun): /reports/failed_scenarios.txt
- Screenshots (on failure, also embedded in report): /screenshots/
- Logs: /logs/automation.log

## Folder Structure
```
OmniBank-BDD-Automation/
├── src/main/java/com/omnibank/automation/
│   ├── base/              (DriverFactory, DriverManager)
│   ├── pages/              (LoginPage, DashboardPage)
│   ├── utilities/          (ConfigReader, Constants, ScreenshotUtils, WaitUtils)
│   ├── listeners/          (RetryAnalyzer, RetryTransformer)
│   └── exceptions/         (FrameworkException)
├── src/test/java/com/omnibank/automation/
│   ├── stepdefinitions/     (Hooks, LoginSteps, DashboardSteps)
│   └── runners/             (TestRunner, RerunFailedTests)
├── src/test/resources/
│   ├── features/            (Login.feature, Dashboard.feature)
│   └── extent.properties
├── src/main/resources/log4j2.xml
├── config/                  (qa.properties, staging.properties, prod.properties)
├── testng.xml
├── pom.xml
├── Jenkinsfile
└── .gitignore
```

## Note on Browser Selection
Browser is read via `System.getProperty("browser", ...)` inside `Hooks.java`, NOT via
TestNG's `@Parameters`/`testng.xml <parameter>` tag - because Cucumber Hooks are not
TestNG-annotated methods, so TestNG parameter injection doesn't reach them. Always pass
the browser via `-Dbrowser=firefox` on the command line (or Jenkins build parameter),
not via a `<parameter>` tag in testng.xml.
