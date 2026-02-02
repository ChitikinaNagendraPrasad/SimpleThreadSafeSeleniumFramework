package com.example.framework.listeners;

import com.aventstack.extentreports.ExtentTest;
import com.example.framework.driver.DriverManager;
import com.example.framework.utils.ScreenshotUtils;
import org.testng.*;

public class ExtentTestNGListener implements ITestListener
{

    private static final ThreadLocal<ExtentTest> TEST_TL = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result)
    {
        ExtentTest test = ExtentManager.getExtent().createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());
        TEST_TL.set(test);
        test.info("ThreadId: " + Thread.currentThread().getId());
    }

    @Override
    public void onTestSuccess(ITestResult result)
    {
        TEST_TL.get().pass("PASSED");
        TEST_TL.remove();
    }

    @Override
    public void onTestFailure(ITestResult result)
    {
        ExtentTest test = TEST_TL.get();
        test.fail(result.getThrowable());

        String path = ScreenshotUtils.capture(DriverManager.getDriver(), result.getMethod().getMethodName());
        if (path != null)
        {
            try
            {
                test.addScreenCaptureFromPath(path);
            } catch (Exception ignored)
            {
            }
        }
        TEST_TL.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result)
    {
        TEST_TL.get().skip("SKIPPED");
        TEST_TL.remove();
    }

    @Override
    public void onFinish(ITestContext context)
    {
        ExtentManager.getExtent().flush();
    }
}
