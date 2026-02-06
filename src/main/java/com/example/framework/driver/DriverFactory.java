package com.example.framework.driver;

import com.example.framework.config.ConfigManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory
{
    private static final Logger log = LogManager.getLogger(DriverFactory.class);

    public WebDriver createDriver()
    {

        // Nag
        //log.info("testng.xml ==> SYS run.mode = {} ", System.getProperty("run.mode"));
        //log.info("config.properties ==> CFG run.mode = {} ", ConfigManager.get("run.mode"));
        // Nag

        String runMode = ConfigManager.get("run.mode");
        String browser = ConfigManager.get("browser");

        if (runMode == null)
            runMode = "local";
        if (browser == null)
            browser = "chrome";

        //log.info("Creating WebDriver | run.mode={} | browser={}", runMode, browser);

        if ("grid".equalsIgnoreCase(runMode))
        {
            return createRemote(browser);
        }
        return createLocal(browser);
    }

    private WebDriver createLocal(String browser)
    {
        switch (browser.toLowerCase())
        {
            case "firefox":
                //WebDriverManager.firefoxdriver().setup();
                return new FirefoxDriver(new FirefoxOptions());
            case "edge":
                //WebDriverManager.edgedriver().setup();
                return new EdgeDriver(new EdgeOptions());
            case "chrome":
            default:
                //WebDriverManager.chromedriver().setup();
                return new ChromeDriver(new ChromeOptions());
        }
    }

    private WebDriver createRemote(String browser)
    {
        String gridUrl = ConfigManager.get("grid.url");
        if (gridUrl == null || gridUrl.isBlank())
        {
            throw new IllegalArgumentException("grid.url is empty but run.mode=grid");
        }

        MutableCapabilities options;
        switch (browser.toLowerCase())
        {
            case "firefox":
                options = new FirefoxOptions();
                break;
            case "edge":
                options = new EdgeOptions();
                break;
            case "chrome":
            default:
                options = new ChromeOptions();
                break;
        }

        // ✅ Hard timeouts to avoid "stuck" during new session
        ClientConfig clientConfig = ClientConfig.defaultConfig().connectionTimeout(Duration.ofSeconds(10)).readTimeout(Duration.ofSeconds(30));

        try
        {
            URL url = new URL(gridUrl);
           // log.info("Attempting Grid session at: {}", url);

            // ✅ Correct way in Selenium 4 to use ClientConfig
            return RemoteWebDriver.builder().address(url).oneOf(options).config(clientConfig).build();

        } catch (MalformedURLException e)
        {
            throw new RuntimeException("Invalid grid.url: " + gridUrl, e);
        } catch (Exception e)
        {
            throw new RuntimeException("Failed to create RemoteWebDriver. grid.url=" + gridUrl + ", browser=" + browser, e);
        }

    }
}
