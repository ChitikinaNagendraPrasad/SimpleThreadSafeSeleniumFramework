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
        Path dest = Path.of("target", "screenshots", fileName);

        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.forceMkdirParent(dest.toFile());
            FileUtils.copyFile(src, dest.toFile());
            return dest.toString();
        } catch (IOException e) {
            return null;
        }
    }
}
