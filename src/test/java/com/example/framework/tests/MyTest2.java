package com.example.framework.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.example.framework.base.BaseTest;
import com.example.framework.base.BaseTestOld;
import com.example.framework.listeners.ExtentManager;
import com.example.framework.pages.HomePage;
import com.example.framework.pages.LoginPage;
import com.example.framework.pages.SignUpPage;

public class MyTest2 extends BaseTest
{
    WebDriver driver;
    HomePage homePage;
    LoginPage loginPage;
    SignUpPage signUpPage;

    public void initializePages()
    {
        try
        {
            homePage = new HomePage();
            loginPage = new LoginPage();
            signUpPage = new SignUpPage();
            step("Launched The Browser And Opened Application");
        } catch (Exception e)
        {
            throw new RuntimeException("Exception In MyTest::initializePages() Method", e);
        }
    }

        
    @Test
    public void testCase1()
    {
        initializePages();

        String emailAddress = "ch.nagendra@nag.com";
        String password = "12345";
        String expectedErrorMessage = "Your email or password is incorrect!";
        
        step("Clicked Signup / Login link");
        loginPage = homePage.clickOn_signupOrLogin_Link();

        step("Entered Username: " + emailAddress);
        loginPage.fillDataIn_loginForm_EmailAddress_TextBox(emailAddress);

        step("Entered Password");
        loginPage.fillDataIn_loginForm_Password_TextBox(password);

        step("Clicked Login button");
        loginPage.clickOn_loginForm_Login_Button();

        step("Expected Error Message : "+expectedErrorMessage+"");
        
        String actualErrorMessage = loginPage.get_EmailOrPassword_Incorrect_Message();
        
        step("Actual Error message : "+actualErrorMessage);
        
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error Message Not Matched");
        step("Validated Error message (\"Your email or password is incorrect!\") -- Success");
    }

    /*
     * public void testCase2() { String methodName =
     * Thread.currentThread().getStackTrace()[1].getMethodName(); try { String
     * email = Utils.generateEmail(); String password = email.replaceAll("\\D",
     * "");
     * 
     * loggingURLInfoInReport();
     * 
     * loginPage = homePage.clickOn_signupOrLogin_Link();
     * loginPage.fillDataIn_signUpForm_Name_TextBox("Nagendra");
     * loginPage.fillDataIn_signUpForm_EmailAddress_TextBox(email); signUpPage =
     * loginPage.clickOn_signUpForm_Signup_Button();
     * signUpPage.clickOn_accountInformation_Mr_RadioButton();
     * signUpPage.fillDataIn_accountInformation_Password_TextBox(password); //
     * signUpPage.selectDOB("12/10/2005"); // System.out.println();
     * 
     * } catch (Exception e) { throw new
     * RuntimeException("Exception In MyTest::" + methodName + "() Method", e);
     * } }
     */
}
