package com.example.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;
import java.nio.file.Path;

public final class ExtentManager
{
    private ExtentManager()
    {
    }

    private static ExtentReports extent;

    public static synchronized ExtentReports getExtent()
    {       
        if (extent == null)
        {
            Path reportPath = Path.of("target", "extent-report", "ExtentReport.html");
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath.toString());
            try
            {
                spark.loadXMLConfig("src/test/resources/extent-config.xml");
            } catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            spark.config().setReportName("Automation Report - Nagendra");
            spark.config().setDocumentTitle("Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Framework", "Selenium + TestNG + POM - Nagendra");
            extent.setSystemInfo("ThreadSafe", "ThreadLocal WebDriver + ThreadLocal ExtentTest");
        }
        return extent;
    }
}
