package com.example.framework.listeners;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public final class CustomExtentManager
{
    private CustomExtentManager()
    {
    }

    private static ExtentReports extent;

    public static synchronized ExtentReports getExtent()
    {
        if (extent == null)
        {
            Path reportPath = Paths.get(System.getProperty("user.dir"), "Reports", "ExtentReport.html");
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath.toString());
            spark.config().setReportName("Simple Thread-Safe Selenium Framework");
            spark.config().setDocumentTitle("Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Framework", "Selenium + TestNG + POM");
            extent.setSystemInfo("ThreadSafe", "ThreadLocal WebDriver + ThreadLocal ExtentTest");
        }
        return extent;
    }
}
