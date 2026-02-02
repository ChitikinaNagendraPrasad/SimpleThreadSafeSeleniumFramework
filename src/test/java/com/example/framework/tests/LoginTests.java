package com.example.framework.tests;

import com.example.framework.base.BaseTest;
import com.example.framework.listeners.RetryAnalyzer;
import com.example.framework.pages.LoginPage;
import com.example.framework.pages.SecureAreaPage;
import com.example.framework.utils.DBUtils;
import com.example.framework.utils.ExcelUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {
    private static final Logger log = LogManager.getLogger(LoginTests.class);

    @DataProvider(name = "excelLoginData", parallel = true)
    public Object[][] excelData() {
        return ExcelUtils.readSheet("testdata/login-data.xlsx", "login");
    }

    @DataProvider(name = "dbLoginData", parallel = true)
    public Object[][] dbData() {
        return DBUtils.fetchLoginData();
    }

    @Test(dataProvider = "excelLoginData", retryAnalyzer = RetryAnalyzer.class)
    public void login_using_excel(String username, String password, String expected) {
        runLoginScenario(username, password, expected);
    }

    @Test(dataProvider = "dbLoginData", retryAnalyzer = RetryAnalyzer.class)
    public void login_using_db(String username, String password, String expected) {
        runLoginScenario(username, password, expected);
    }

    private void runLoginScenario(String username, String password, String expected) {
        log.info("Running login scenario | user={} | expected={}", username, expected);

        LoginPage login = new LoginPage();
        SecureAreaPage secure = login
                .enterUsername(username)
                .enterPassword(password)
                .submitLogin();

        String msg = secure.getFlashMessage();

        if ("success".equalsIgnoreCase(expected)) {
            Assert.assertTrue(msg.contains("You logged into a secure area"), "Expected success message. Actual: " + msg);
        } else {
            Assert.assertTrue(msg.toLowerCase().contains("invalid"), "Expected invalid message. Actual: " + msg);
        }
    }
}
