package com.omnibank.automation.listeners;

import com.omnibank.automation.utilities.Constants;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Since the Cucumber runner extends AbstractTestNGCucumberTests, every Gherkin
 * scenario runs internally as a TestNG @Test method (via the scenarios() DataProvider).
 * That means this same RetryAnalyzer, originally designed for plain TestNG tests,
 * applies automatically to Cucumber scenarios too - retrying a failed scenario
 * up to Constants.RETRY_COUNT times before letting it fail permanently.
 */
public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < Constants.RETRY_COUNT) {
            retryCount++;
            System.out.println("Retrying scenario: " + result.getMethod().getMethodName()
                    + " | Attempt: " + retryCount);
            return true;
        }
        return false;
    }
}
