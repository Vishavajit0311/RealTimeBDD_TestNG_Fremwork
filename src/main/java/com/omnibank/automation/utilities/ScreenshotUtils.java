package com.omnibank.automation.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtils {

    /**
     * Saves a screenshot to disk under /screenshots/ and returns the path.
     * Used in addition to Cucumber's scenario.attach() in Hooks.java, so screenshots
     * are available both as standalone files AND embedded inline in the Extent report.
     */
    public static String captureScreenshot(WebDriver driver, String scenarioName) {
        String safeName = scenarioName.replaceAll("[^a-zA-Z0-9-_]", "_");
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destinationPath = Constants.SCREENSHOT_DIR + safeName + "_" + timestamp + ".png";

        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(source, new File(destinationPath));
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }

        return destinationPath;
    }
}
