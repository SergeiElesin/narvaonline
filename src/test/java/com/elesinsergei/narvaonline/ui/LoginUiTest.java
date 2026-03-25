package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("UI Tests")
@Feature("Authentication")
public class LoginUiTest extends BaseTest {

    LoginPage loginPage = new LoginPage();

    @Test
    @Story("Login with valid credentials")
    @DisplayName("Successful login to the admin panel")
    @Step("Successful autentication")
    public void shouldLoginAsAdmin() {
        loginPage.openPage()
                .login(USER_NAME, PASSWORD); // Тут подставь свои данные

        loginPage.shouldBeLoggedIn();

        loginPage.fastLogout();
    }

}
