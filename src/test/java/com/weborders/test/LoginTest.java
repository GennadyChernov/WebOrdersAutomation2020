package com.weborders.test;

import com.weborders.pages.AbstractBasePage;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class LoginTest extends AbstractBaseTest {
    @Test
    public void login(){
        LoginTest loginPage = new LoginTest();
        loginPage.login();
        assertEquals(loginPage.getPageLogoText(), "Web Orders");
    }
}
