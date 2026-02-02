package com.example.framework.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


public final class ScreenshotUtils {
    private ScreenshotUtils() {}

    public static String capture(WebDriver driver, String testName) {
        if (driver == null) return null;

        String fileName = testName + "_T" + Thread.currentThread().getId() + "_" + System.currentTimeMillis() + ".png";

        // Save screenshots to target/screenshots
        Path screenshotPath = Path.of("target", "screenshots", fileName);

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.forceMkdirParent(screenshotPath.toFile());
            FileUtils.copyFile(src, screenshotPath.toFile());

            // Report is in target/extent-report, so image should be referenced as ../screenshots/<file>
            return "../screenshots/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

