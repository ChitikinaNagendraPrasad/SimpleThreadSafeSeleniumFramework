package com.example.framework.pages;

import com.example.framework.driver.DriverManager;
import com.example.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPageOld {
    private final WebDriver driver;

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginBtn = By.cssSelector("button[type='submit']");
    private final By flash = By.id("flash");

    public LoginPageOld() {
        this.driver = DriverManager.getDriver();
    }

    public LoginPageOld enterUsername(String value) {
        WaitUtils.visible(username).clear();
        WaitUtils.visible(username).sendKeys(value);
        return this;
    }

    public LoginPageOld enterPassword(String value) {
        WaitUtils.visible(password).clear();
        WaitUtils.visible(password).sendKeys(value);
        return this;
    }

    public SecureAreaPageOld submitLogin() {
        WaitUtils.click(loginBtn);
        return new SecureAreaPageOld();
    }

    public String getFlashMessage() {
        return WaitUtils.visible(flash).getText();
    }

    public boolean isAt() {
        return driver.getTitle() != null; // simple check
    }
}
