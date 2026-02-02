package com.example.framework.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// ...

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class CustomScreenShotUtils
{
    private CustomScreenShotUtils()
    {
    }


    private static Path screenshotsDir() {
        return Paths.get(System.getProperty("user.dir"), "Screenshots", RunInfo.runId());
    }

    public static String capture(WebDriver driver, String testName) {
        if (driver == null) return null;

        String fileName = testName
                + "_T" + Thread.currentThread().getId()
                + "_" + System.currentTimeMillis()
                + ".png";

        Path dest = screenshotsDir().resolve(fileName);

        try {
            Files.createDirectories(dest.getParent());
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, dest.toFile());
            return dest.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
