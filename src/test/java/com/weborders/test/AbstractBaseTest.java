package com.weborders.test;

import com.weborders.utilities.BrowserUtilities;
import com.weborders.utilities.ConfigurationReader;
import com.weborders.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import javax.security.auth.login.Configuration;

public abstract class AbstractBaseTest {
    protected WebDriver driver = Driver.getDriver();

    @BeforeMethod
    public void setup() {
        driver.get(ConfigurationReader.getProperty("url"));
        driver.manage().window().maximize();
    }


    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
         String screenshotLocation =  BrowserUtilities.getScreenshot(testResult.getName());
        }

        Driver.closeDriver();
    }

}

