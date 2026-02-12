
package com.example.framework.listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.example.framework.driver.DriverManager;
import com.example.framework.utils.ScreenshotUtils;

public class ExtentTestNGListener implements ITestListener
{

    @Override
    public void onTestStart(ITestResult result)
    {
        ExtentTest test = ExtentManager.getExtent()
                .createTest(result.getTestClass().getName() + " :: " + result.getMethod().getMethodName());

        ExtentStepLogger.setTest(test);
        test.info("ThreadId: " + Thread.currentThread().getId());
    }

    @Override
    public void onTestSuccess(ITestResult result)
    {
        ExtentTest test = ExtentStepLogger.getTest();
        if (test != null)
            test.pass("TEST PASSED");
        ExtentStepLogger.unload();
    }

    @Override
    public void onTestFailure(ITestResult result)
    {
        ExtentTest test = ExtentStepLogger.getTest();
        ExtentTest lastStep = ExtentStepLogger.getLastStep();

        // Mark last logged step as FAIL (this is Option A behavior)
        if (lastStep != null)
        {
            lastStep.fail("Step Failed: " + result.getThrowable());
        }

        if (test != null)
        {
            test.fail(result.getThrowable());
        }

        // Screenshot
        String path = ScreenshotUtils.capture(DriverManager.getDriver(), result.getMethod().getMethodName());
        if (path != null)
        {
            try
            {
                if (lastStep != null)
                    lastStep.addScreenCaptureFromPath(path, "Failure Screenshot");
                else
                    if (test != null)
                        test.addScreenCaptureFromPath(path, "Failure Screenshot");
            }
            catch (Exception ignored)
            {
            }
        }

        ExtentStepLogger.unload();
    }

    @Override
    public void onTestSkipped(ITestResult result)
    {
        ExtentTest test = ExtentStepLogger.getTest();
        if (test != null)
        {
            Throwable t = result.getThrowable();
            if (t != null)
            {
                test.skip(t); // <-- logs full exception + stack trace
            }
            else
            {
                test.skip("TEST SKIPPED"); // <-- fallback if no throwable
            }
        }
        ExtentStepLogger.unload();
    }

    @Override
    public void onFinish(ITestContext context)
    {
        ExtentManager.getExtent().flush();
    }
}
