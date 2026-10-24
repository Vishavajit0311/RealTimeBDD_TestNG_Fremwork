package com.omnibank.automation.base;

import org.openqa.selenium.WebDriver;

/**
 * Cucumber instantiates Hooks and Step Definition classes as SEPARATE objects
 * for the same scenario (via dependency injection). Unlike TestNG's BaseTest,
 * there's no shared "this.driver" field between them - so a ThreadLocal holder
 * is used instead. This also makes parallel scenario execution thread-safe,
 * since each thread gets its own isolated WebDriver reference.
 */
public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // prevents memory leaks / stale references across threads
        }
    }
}
