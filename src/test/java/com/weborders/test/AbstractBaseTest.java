package com.weborders.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.weborders.utilities.BrowserUtilities;
import com.weborders.utilities.ConfigurationReader;
import com.weborders.utilities.Driver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import static org.testng.Assert.*;

import javax.security.auth.login.Configuration;
import java.io.IOException;

public abstract class AbstractBaseTest {
    protected WebDriver driver = Driver.getDriver();
    protected ExtentReports extentReports;
    protected ExtentHtmlReporter extentHtmlReporter;
    protected static ExtentTest extentTest;


    @BeforeTest
    public void beforeTest() {
        extentReports = new ExtentReports();
        String reportPath = "";
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            reportPath = System.getProperty("user.dir") + "\\test-output\\report.html";
        } else {
            reportPath = System.getProperty("user.dir") + "/test-output/report.html";
        }
    }

    @AfterTest
    public void afterTest() {
        extentReports.flush();
    }

    @BeforeMethod
    public void setup() {
        driver.get(ConfigurationReader.getProperty("url"));
        driver.manage().window().maximize();
    }


    @AfterMethod
    public void tearDown(ITestResult testResult) {
        if (testResult.getStatus() == ITestResult.FAILURE) {
            String screenshotLocation = BrowserUtilities.getScreenshot(testResult.getName());
            try {
                extentTest.fail(testResult.getName());
                extentTest.addScreenCaptureFromPath(screenshotLocation);
                extentTest.fail(testResult.getThrowable());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to attach screenshot");
            }
        } else if (testResult.getStatus() == ITestResult.SUCCESS) {
            extentTest.pass(testResult.getName());
        } else if (testResult.getStatus() == ITestResult.SKIP) {
            extentTest.skip(testResult.getName());
        }
        BrowserUtilities.wait(3);
        Driver.closeDriver();
    }
    
}

