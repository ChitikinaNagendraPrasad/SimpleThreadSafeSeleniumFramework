package com.example.framework.pages;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.example.framework.driver.DriverManager;
import com.example.framework.utils.WaitUtils;

public class LoginPage
{
    private final WebDriver driver;

    public LoginPage()
    {
        this.driver = DriverManager.getDriver();
    }
    // Page Properties

    // Login Form
    By loginForm_EmailAddress_TextBox = By.xpath("//div[@class=\"login-form\"]//input[@name='email']");
    By loginForm_Password_TextBox = By.xpath("//div[@class=\"login-form\"]//input[@name='password']");
    By loginForm_Login_Button = By.xpath("//div[@class=\"login-form\"]//button[contains(text(),'Login')]");

    // Error Messages
    By errorMsg_loginForm_EmailOrPassword_Incorrect = By.xpath("//p[normalize-space()=\"Your email or password is incorrect!\"]");

    public LoginPage fillDataIn_loginForm_EmailAddress_TextBox(String email)
    {
        WaitUtils.visible(loginForm_EmailAddress_TextBox).clear();
        WaitUtils.visible(loginForm_EmailAddress_TextBox).sendKeys(email);
        return this;
    }

    public LoginPage fillDataIn_loginForm_Password_TextBox(String password)
    {
        WaitUtils.visible(loginForm_Password_TextBox).clear();
        WaitUtils.visible(loginForm_Password_TextBox).sendKeys(password);
        return this;
    }

    public LoginPage clickOn_loginForm_Login_Button()
    {
        WaitUtils.click(loginForm_Login_Button);
        return this;
    }

    public String get_EmailOrPassword_Incorrect_Message()
    {
        String returnValue = null;
        returnValue = WaitUtils.visible(errorMsg_loginForm_EmailOrPassword_Incorrect).getText();
        return returnValue;
    }

    // SignUp Form

    By signUpForm_Name_TextBox = By.xpath("//div[@class=\"signup-form\"]//input[@name='name']");
    By signUpForm_EmailAddress_TextBox = By.xpath("//div[@class=\"signup-form\"]//input[@name='email']");
    By signUpForm_Signup_Button = By.xpath("//div[@class=\"signup-form\"]//button[@data-qa='signup-button']");

    public LoginPage fillDataIn_signUpForm_Name_TextBox(String name)
    {
        WaitUtils.visible(signUpForm_Name_TextBox).clear();
        WaitUtils.visible(signUpForm_Name_TextBox).sendKeys(name);
        return this;
    }

    public LoginPage fillDataIn_signUpForm_EmailAddress_TextBox(String emailAddress)
    {
        WaitUtils.visible(signUpForm_EmailAddress_TextBox).clear();
        WaitUtils.visible(signUpForm_EmailAddress_TextBox).sendKeys(emailAddress);
        return this;
    }

    public void clickOn_signUpForm_Signup_Button()
    {
        WaitUtils.click(signUpForm_Signup_Button);
        // return SignUpPage;
    }
}
