package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;

import java.sql.SQLOutput;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


/**
 * Page Object for LoginPage
 * Contains methods and data for login and logout via logon form on UI
 */
public class LoginPage {

    private final SelenideElement loginField = $("input[name^='username-']");
    private final SelenideElement passwordField = $("input[name^='user_password-']");

    //private final SelenideElement submitButton = $("#wp-submit");
    private final SelenideElement submitButton = $("#um-submit-btn");

    // visible only for logged users
    private final SelenideElement adminMenu = $("#adminmenu");

    public LoginPage openPage() {
        //open("/wp-login.php");
        open("/loginly5r21p08qka3gpsoufk3o95/");
        return this;
    }

    /**
     * Login with username and password on frontend
     */
    //@Step("Login")
    public void login(String user, String password) {

        step("Login via username and password", () -> {
            // Set off Allure Listener
            SelenideLogger.removeListener("AllureSelenide");

            try {
                loginField.shouldBe(visible, Duration.ofSeconds(10)).setValue(user);
                passwordField.shouldBe(visible).setValue(password);
                submitButton.pressEnter();
            } finally {
                // Set on Allure Listener back
                SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
            }
        });
    }

//unsafe method - can see username and password in Allure Report
    /*public void login(String user, String password) {
        step("Login via username and password", () -> {
            $("#user").setValue(user);
            $("#pass").setValue(password);
            loginField.shouldBe(visible, Duration.ofSeconds(10)).setValue(user);
            passwordField.shouldBe(visible).setValue(password);
            submitButton.pressEnter();
        });
    }*/

    /**
     * Login confirmation
     */
    @Step("Login confirmation")
    public void shouldBeLoggedIn() {
        adminMenu.shouldBe(visible, Duration.ofSeconds(15)); // Если меню админки видно — логин прошел успешно
    }

    /**
     * Logout via UI
     */
    public void logoutViaUI() {
        // 1. Наводим мышь на профиль (обычно ID #wp-admin-bar-my-account)
        $("#wp-admin-bar-my-account").hover();

        // 2. Wait for "Log Out" and click
        $("#wp-admin-bar-logout a").shouldBe(Condition.visible).click();

        // 3. Check: this is login page
        $("#loginform").shouldBe(Condition.visible);
    }

    /**
     * Fast logout - clean session
     */
    @Step("Fast logout")
    public void fastLogout() {
        clearBrowserCookies();
        refresh();
    }

    /**
     * Logout via URL
     */
    public void logoutViaUrl() {
        open("/wp-login.php?action=logout");

        // If confirmation needed
        if ($("a[href*='action=logout']").exists()) {
            $("a[href*='action=logout']").click();
        }
    }


}