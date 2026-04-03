package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.HomePage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * HomeUiTest - test class for Home page
 */

@Epic("UI Tests")
@Feature("Main Page")
@Owner("Sergei Elesin")
public class HomeUiTest extends BaseTest {

    HomePage homePage = new HomePage();

    @Test
    @DisplayName("Check main page title")
    @Description("Open main page and check page title")
    @Step("Checking page title: {expectedTitle}")
    void testNarvaonlineTitle() {
        homePage.openPage().verifyPageTitle("НАРВА ОНЛАЙН - городской портал | Здесь всё!");
    }
}
