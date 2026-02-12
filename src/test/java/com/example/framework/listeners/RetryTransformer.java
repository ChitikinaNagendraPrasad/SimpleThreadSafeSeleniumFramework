package com.example.framework.listeners;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RetryTransformer implements IAnnotationTransformer
{
    @Override
    public void transform(ITestAnnotation annotation, @SuppressWarnings("rawtypes") Class testClass,
            @SuppressWarnings("rawtypes") Constructor testConstructor, Method testMethod)
    {
        // Apply our analyzer to every @Test
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }
}