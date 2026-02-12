package com.example.framework.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager
{
    private static final Properties PROPS = new Properties();

    static
    {
        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties"))
        {
            if (is == null)
            {
                throw new RuntimeException("config.properties not found in test resources");
            }
            PROPS.load(is);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private ConfigManager()
    {
    }

    public static String get(String key)
    {
        // Allow override via -Dkey=value
        String sys = System.getProperty(key);
        return (sys != null && !sys.isBlank()) ? sys : PROPS.getProperty(key);
    }

    public static String getWithSource(String key)
    {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank())
        {
            return sys + " (source=SystemProperty)";
        }
        String prop = PROPS.getProperty(key);
        return prop + " (source=config.properties)";
    }

    public static int getInt(String key, int defaultVal)
    {
        try
        {
            String v = get(key);
            return (v == null) ? defaultVal : Integer.parseInt(v.trim());
        }
        catch (Exception e)
        {
            return defaultVal;
        }
    }
}
