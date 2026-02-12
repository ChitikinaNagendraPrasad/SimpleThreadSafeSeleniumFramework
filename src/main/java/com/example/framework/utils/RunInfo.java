package com.example.framework.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class RunInfo
{
    private RunInfo()
    {
    }

    // Generated once when the class is loaded (same ID for the entire run)
    private static final String RUN_ID = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

    public static String runId()
    {
        return RUN_ID;
    }
}