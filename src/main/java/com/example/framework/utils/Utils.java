package com.example.framework.utils;


import org.testng.Assert;

public class Utils
{

    public static boolean validateErrorMessage(String actualErrorMessage, String expectedErrorMessage)
    {
        boolean returnValue=false;        
        try
        {
            if(actualErrorMessage.equals(expectedErrorMessage))
            returnValue=true;
            else
            returnValue=false;
        } catch (Exception e)
        {
            throw e;
        }
        return returnValue;
    }

}
