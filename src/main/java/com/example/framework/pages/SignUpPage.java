package com.example.framework.pages;

import java.time.Duration;
import java.time.Month;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.example.framework.driver.DriverManager;
import com.example.framework.utils.WaitUtils;


public class SignUpPage
{
    
    private final WebDriver driver;

    public SignUpPage()
    {
        this.driver = DriverManager.getDriver();
    }
    /*
     * WebDriver driver; Wait<WebDriver> wait;
     */

    /*
     * public SignUpPage(WebDriver driver) { try { this.driver = driver; wait =
     * new
     * FluentWait<>(driver).withTimeout(Duration.ofSeconds(20)).pollingEvery(
     * Duration.ofMillis(300)).ignoring(NoSuchElementException.class).ignoring(
     * StaleElementReferenceException.class).ignoring(
     * ElementClickInterceptedException.class); } catch (Exception e) {
     * e.printStackTrace(); } }
     */

    //Enter Account Information
    By accountInformation_Mr_RadioButton = By.xpath("//div[@class=\"login-form\"]//input[@id='id_gender1']");
    By accountInformation_Mrs_RadioButton = By.xpath("//div[@class=\"login-form\"]//input[@id='id_gender2']");
    By accountInformation_Name_TextBox = By.xpath("//div[@class=\"login-form\"]//input[@id='name']");
    By accountInformation_Email_TextBox = By.xpath("//div[@class=\"login-form\"]//input[@id='email']");
    By accountInformation_Password_TextBox = By.xpath("//div[@class=\"login-form\"]//input[@id='password']");
    
    /*
     * By accountInformation_DOB_Days_DropDown =
     * By.xpath("(//div[@class=\"form-group\"]//select)[1]"); By
     * accountInformation_DOB_Months_DropDown =
     * By.xpath("(//div[@class=\"form-group\"]//select)[2]"); By
     * accountInformation_DOB_Years_DropDown =
     * By.xpath("(//div[@class=\"form-group\"]//select)[3]");
     */
    
    By accountInformation_DOB_Days_DropDown = By.xpath("//select[@id='days']");
    By accountInformation_DOB_Months_DropDown = By.xpath("//select[@id='months']");
    By accountInformation_DOB_Years_DropDown = By.xpath("//select[@id='years']");
    
    
    By accountInformation_SignUp_NewsLetter_CheckBox = By.xpath("//div[@class=\"login-form\"]//input[@id='newsletter']");
    By accountInformation_Receive_SpecialOffer_CheckBox = By.xpath("//div[@class=\"login-form\"]//input[@id='optin']");
    
    //Address Information
    
    
    public SignUpPage clickOn_accountInformation_Mr_RadioButton()
    {
        WaitUtils.click(accountInformation_Mr_RadioButton);
        return this;
    }
    
    public SignUpPage clickOn_accountInformation_Mrs_RadioButton()
    {
        WaitUtils.click(accountInformation_Mrs_RadioButton);
        return this;
    }
     
    public SignUpPage fillDataIn_accountInformation_Name_TextBox(String name)
    {
        WaitUtils.visible(accountInformation_Name_TextBox).clear();
        WaitUtils.visible(accountInformation_Name_TextBox).sendKeys(name);
        return this;
    }
    
    public SignUpPage fillDataIn_accountInformation_Email_TextBox(String email)
    {
        WaitUtils.visible(accountInformation_Email_TextBox).clear();
        WaitUtils.visible(accountInformation_Email_TextBox).sendKeys(email);
        return this;
    }
    
    public SignUpPage fillDataIn_accountInformation_Password_TextBox(String password)
    {
        WaitUtils.visible(accountInformation_Password_TextBox).clear();
        WaitUtils.visible(accountInformation_Password_TextBox).sendKeys(password);
        return this;
    }
    
    /*
     * public void selectDOB(String dob) { String days = dob.split("/")[0];
     * //String months = Month.of(Integer.parseInt(dob.split("/")[1])).name();
     * String months = dob.split("/")[1]; String years = dob.split("/")[2]; try
     * { Utils.printMessageWithValue("Days", days);
     * Utils.printMessageWithValue("Months", months);
     * Utils.printMessageWithValue("Years", years);
     * 
     * Select days_DropDown = new
     * Select(driver.findElement(accountInformation_DOB_Days_DropDown) );
     * days_DropDown.selectByVisibleText(days); Thread.sleep(5000); Select
     * months_DropDown = new
     * Select(driver.findElement(accountInformation_DOB_Months_DropDown));
     * days_DropDown.selectByIndex(Integer.parseInt(months));
     * Thread.sleep(5000); Select years_DropDown = new
     * Select(driver.findElement(accountInformation_DOB_Years_DropDown));
     * days_DropDown.selectByVisibleText(years); Thread.sleep(5000); }
     * catch(Exception e) { throw new
     * RuntimeException("DOB selection failed for: " + dob, e); } }
     */
}
