package com.omnibank.automation.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.omnibank.automation.stepdefinitions"},
        tags = "@regression or @smoke",
        plugin = {
                "pretty",
                "html:reports/cucumber-html-report.html",
                "json:reports/cucumber.json",
                "rerun:reports/failed_scenarios.txt",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // Overridden with parallel = true so scenarios run in parallel threads,
    // matching the parallel="tests" / thread-count in testng.xml.
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
//    public abstract class AbstractTestNGCucumberTests {
//
//        @Test(dataProvider = "scenarios")
//        public void runScenario(PickleWrapper pickle, FeatureWrapper feature) {
//            // internally invokes Cucumber's own scenario execution engine
//            runner.runPickle(pickle.getPickle());
//        }
//
//        @DataProvider(parallel = false)
//        public Object[][] scenarios() {
//            // reads ALL .feature files, converts each scenario into one row
//            // of test data: { pickleWrapper, featureWrapper }
//        }
//    }



}
