package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.HomePage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * HomeUiTest - test class for Home page
 */
@Tag("ui")
@Epic("UI Tests")
@Feature("Main Page")
@Owner("Sergei Elesin")
public class HomeUiTest extends BaseTest {

    LoginPage loginPage = new  LoginPage();
    HomePage homePage = new HomePage();

    /**
     * 1.Open main page
     * 2.Verify page title on main page
     */
    @Test
    @DisplayName("Check main page title")
    @Description("Open main page and check page title")
    @Step("Checking page title: {expectedTitle}")
    void testNarvaonlineTitle() {
        homePage.openPage().verifyPageTitle("НАРВА ОНЛАЙН - городской портал | Здесь всё!");

        loginPage.fastLogout();
    }

}
