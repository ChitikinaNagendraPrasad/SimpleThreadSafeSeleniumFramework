package com.example.framework.base;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.example.framework.config.ConfigManager;
import com.example.framework.driver.DriverFactory;
import com.example.framework.driver.DriverManager;
import com.example.framework.listeners.ExtentStepLogger;

public abstract class BaseTestOld
{
    private static final Logger log = LogManager.getLogger(BaseTestOld.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method)
    {
        WebDriver driver = new DriverFactory().createDriver();
        DriverManager.setDriver(driver);

        driver.manage().window().maximize();
        driver.get(ConfigManager.get("base.url"));
        log.info("Opened URL: {}", ConfigManager.get("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        WebDriver driver = DriverManager.getDriver();
        try
        {
            if (driver != null)
            {
                driver.quit();
                log.info("Driver quit successfully");
            }
        } finally
        {
            DriverManager.unload();
        }
    }

    protected void step(String message)
    {
        ExtentStepLogger.step(message);
    }

    protected void info(String message)
    {
        ExtentStepLogger.info(message);
    }

}
