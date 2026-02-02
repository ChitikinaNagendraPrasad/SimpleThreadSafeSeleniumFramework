package com.example.framework.pages;

import com.example.framework.utils.WaitUtils;
import org.openqa.selenium.By;

public class SecureAreaPage {
    private final By flash = By.id("flash");
    private final By logout = By.cssSelector("a.button.secondary.radius");

    public String getFlashMessage() {
        return WaitUtils.visible(flash).getText();
    }

    public void logout() {
        WaitUtils.click(logout);
    }
}
