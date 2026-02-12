package com.example.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.example.framework.driver.DriverManager;
import com.example.framework.utils.WaitUtils;

public class HomePage
{
    private final WebDriver driver;

    public HomePage()
    {
        this.driver = DriverManager.getDriver();
    }

    // Page Properties

    By home_Link = By.xpath("//a[normalize-space()=\"Home\"]");
    By products_Link = By.xpath("//a[@href=\"/products\"]");
    By cart_Link = By.xpath("//a[normalize-space()=\"Cart\"]");
    By signupOrLogin_Link = By.xpath("//a[normalize-space()=\"Signup / Login\"]");
    By testCases_Link = By.xpath("//a[contains(text(),\"Test Cases\")]");
    By apiTesting_Link = By.xpath("//a[normalize-space()=\"API Testing\"]");

    // Page Methods
    public HomePage clickOn_home_Link()
    {
        WaitUtils.click(home_Link);
        return this;
    }

    public void clickOn_products_Link()
    {
        WaitUtils.click(home_Link);
    }

    public void clickOn_cart_Link()
    {
        WaitUtils.click(cart_Link);
    }

    public LoginPage clickOn_signupOrLogin_Link()
    {
        WaitUtils.click(signupOrLogin_Link);
        return new LoginPage();
    }

    public void clickOn_testCases_Link()
    {
        WaitUtils.click(testCases_Link);
    }

    public void clickOn_apiTesting_Link()
    {
        WaitUtils.click(apiTesting_Link);
    }
}
