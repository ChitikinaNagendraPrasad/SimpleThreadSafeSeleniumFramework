package com.example.framework.listeners;

import com.aventstack.extentreports.ExtentTest;

public final class ExtentStepLogger
{

    private ExtentStepLogger()
    {
    }

    private static final ThreadLocal<ExtentTest> TEST_TL = new ThreadLocal<>();
    private static final ThreadLocal<ExtentTest> LAST_STEP_TL = new ThreadLocal<>();
    private static final ThreadLocal<Integer> STEP_NO_TL = ThreadLocal.withInitial(() -> 0);

    public static void setTest(ExtentTest test)
    {
        TEST_TL.set(test);
        LAST_STEP_TL.remove();
        STEP_NO_TL.set(0);
    }

    public static ExtentTest getTest()
    {
        return TEST_TL.get();
    }

    public static ExtentTest getLastStep()
    {
        return LAST_STEP_TL.get();
    }

    public static void unload()
    {
        TEST_TL.remove();
        LAST_STEP_TL.remove();
        STEP_NO_TL.remove();
    }

    /** Creates a step node and marks it INFO immediately. */
    public static void step(String message)
    {
        ExtentTest test = TEST_TL.get();
        if (test == null)
            return;

        int n = STEP_NO_TL.get() + 1;
        STEP_NO_TL.set(n);

        ExtentTest stepNode = test.createNode("Step " + n + ": " + message);
        stepNode.info(message);

        LAST_STEP_TL.set(stepNode);
    }

    /** Optional helper for extra details without creating a new step. */
    public static void info(String message)
    {
        ExtentTest test = TEST_TL.get();
        if (test != null)
            test.info(message);
    }
}
