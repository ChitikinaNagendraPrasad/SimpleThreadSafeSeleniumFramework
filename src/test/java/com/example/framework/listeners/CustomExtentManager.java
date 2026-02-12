package com.example.framework.listeners;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.example.framework.utils.RunInfo;

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
            Path reportPath = Paths.get(System.getProperty("user.dir"), "Reports", RunInfo.runId(), "ExtentReport.html");

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath.toString());
            spark.config().setReportName("Automation Execution Report");
            spark.config().setDocumentTitle("Extent Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("RunId", RunInfo.runId());
            extent.setSystemInfo("Framework", "Selenium + TestNG + POM");
            extent.setSystemInfo("Parallel", "ThreadLocal WebDriver");
        }
        return extent;
    }
}
