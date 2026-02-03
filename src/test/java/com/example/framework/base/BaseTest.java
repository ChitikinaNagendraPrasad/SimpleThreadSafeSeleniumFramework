
package com.example.framework.base;

import com.example.framework.config.ConfigManager;
import com.example.framework.driver.DriverFactory;
import com.example.framework.driver.DriverManager;
import com.example.framework.listeners.ExtentStepLogger;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest
{

    private static final Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method, ITestContext ctx)
    {

        // HARD PROOF BaseTest is running
        System.out.println(">>> BaseTest.setUp executed from test class: " + this.getClass().getName());

        // Read parameters (may be null if suite xml not used by runner)
        String xmlRunMode = ctx.getCurrentXmlTest().getParameter("run.mode");
        String xmlBrowser = ctx.getCurrentXmlTest().getParameter("browser");
        String xmlGridUrl = ctx.getCurrentXmlTest().getParameter("grid.url");
        String xmlBaseUrl = ctx.getCurrentXmlTest().getParameter("base.url");
        String xmlImplicit = ctx.getCurrentXmlTest().getParameter("implicit.wait.seconds");
        String xmlExplicit = ctx.getCurrentXmlTest().getParameter("explicit.wait.seconds");

        // Copy XML -> System properties ONLY if present (do not overwrite -D
        // values)
        setSysIfAbsent("run.mode", xmlRunMode);
        setSysIfAbsent("browser", xmlBrowser);
        setSysIfAbsent("grid.url", xmlGridUrl);
        setSysIfAbsent("base.url", xmlBaseUrl);
        setSysIfAbsent("implicit.wait.seconds", xmlImplicit);
        setSysIfAbsent("explicit.wait.seconds", xmlExplicit);

        /*
         * // PROOF logs System.out.println(
         * "****************************************************************************************************************************************************"
         * ); System.out.println(
         * "****************************************************************************************************************************************************"
         * ); log.info("BASETEST PROOF | XmlSuite file = {}",
         * ctx.getSuite().getXmlSuite().getFileName());
         * log.info("BASETEST PROOF | XML run.mode = {}", xmlRunMode);
         * log.info("BASETEST PROOF | SYS run.mode = {}",
         * System.getProperty("run.mode"));
         * log.info("BASETEST PROOF | CFG run.mode = {}",
         * ConfigManager.get("run.mode")); System.out.println(
         * "****************************************************************************************************************************************************"
         * ); log.info("BASETEST PROOF | XML browser  = {}", xmlBrowser);
         * log.info("BASETEST PROOF | SYS browser  = {}",
         * System.getProperty("browser"));
         * log.info("BASETEST PROOF | CFG browser  = {}",
         * ConfigManager.get("browser")); System.out.println(
         * "****************************************************************************************************************************************************"
         * ); System.out.println(
         * "****************************************************************************************************************************************************"
         * );
         */
        // Create driver AFTER config is ready
        WebDriver driver = new DriverFactory().createDriver();
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
        step("Launched the Browser and Opened Application");
        driver.get(ConfigManager.get("base.url"));
        log.info("Opened URL: {}", ConfigManager.get("base.url"));
    }

    private void setSysIfAbsent(String key, String xmlVal)
    {
        // If already provided via -D, keep it. Otherwise set from XML (if
        // present).
        String current = System.getProperty(key);
        if ((current == null || current.isBlank()) && xmlVal != null && !xmlVal.isBlank())
        {
            System.setProperty(key, xmlVal);
        }
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
