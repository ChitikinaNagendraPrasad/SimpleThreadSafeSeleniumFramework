package com.example.framework.utils;

import com.example.framework.config.ConfigManager;
import com.example.framework.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {
    private WaitUtils() {}

    private static WebDriverWait waitObj() {
        int seconds = ConfigManager.getInt("explicit.wait.seconds", 10);
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds));
    }

    public static WebElement visible(By locator) {
        return waitObj().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void click(By locator) {
        waitObj().until(ExpectedConditions.elementToBeClickable(locator)).click();
    }
}
