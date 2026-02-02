package com.example.framework.pages;

import com.example.framework.driver.DriverManager;
import com.example.framework.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private final WebDriver driver;

    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginBtn = By.cssSelector("button[type='submit']");
    private final By flash = By.id("flash");

    public LoginPage() {
        this.driver = DriverManager.getDriver();
    }

    public LoginPage enterUsername(String value) {
        WaitUtils.visible(username).clear();
        WaitUtils.visible(username).sendKeys(value);
        return this;
    }

    public LoginPage enterPassword(String value) {
        WaitUtils.visible(password).clear();
        WaitUtils.visible(password).sendKeys(value);
        return this;
    }

    public SecureAreaPage submitLogin() {
        WaitUtils.click(loginBtn);
        return new SecureAreaPage();
    }

    public String getFlashMessage() {
        return WaitUtils.visible(flash).getText();
    }

    public boolean isAt() {
        return driver.getTitle() != null; // simple check
    }
}
