package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * LoginUiTest - test class for login page
 */
@Tag("ui")
@Epic("UI Tests")
@Feature("Authentication")
public class LoginUiTest extends BaseTest {

    LoginPage loginPage = new LoginPage();

    /**
     * 1.Login via login and password on frontend
     * 2.Verify login on frontend
     * 3.Fast logout
     */
    @Test
    @Story("Login with valid credentials")
    @DisplayName("Successful login to the admin panel")
    @Step("Successful authentication")
    public void shouldLoginAsAdminTest() {
        loginPage.openPage()
                .login(USER_NAME, PASSWORD); // Тут подставь свои данные

        loginPage.shouldBeLoggedIn();

        loginPage.fastLogout();
    }

    /**
     * 1.Login via login and password on frontend with cookies
     * 3.Verify login on frontend
     * 3.Fast logout
     */
    @Test
    @DisplayName("Login with cookies")
    @Story("Login with cookies and test via UI")
    @Step("Successful authentication with cookies")
    public void loginWithCookiesTest(){
        loginPage.loginWithCookies(USER_NAME, PASSWORD, BASE_URL, BASE_URL + "/loginly5r21p08qka3gpsoufk3o95");

        loginPage.shouldBeLoggedIn();

        loginPage.fastLogout();
    }
}
