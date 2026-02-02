package com.example.framework.driver;

import org.openqa.selenium.WebDriver;

public final class DriverManager {
    private DriverManager() {}

    private static final ThreadLocal<WebDriver> DRIVER_TL = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return DRIVER_TL.get();
    }

    public static void setDriver(WebDriver driver) {
        DRIVER_TL.set(driver);
    }

    public static void unload() {
        DRIVER_TL.remove();
    }
}
