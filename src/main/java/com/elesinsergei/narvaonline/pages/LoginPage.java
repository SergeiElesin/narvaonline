package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {

    // Селекторы стандартного WordPress логина
    //private final SelenideElement loginField = $("#user_login");
    //private final SelenideElement loginField = $("#username-3261");
    private final SelenideElement loginField = $("input[name^='username-']");

    //private final SelenideElement passwordField = $("#user_pass");
    //private final SelenideElement passwordField = $("#user_password-3261");
    private final SelenideElement passwordField = $("input[name^='user_password-']");

    //private final SelenideElement submitButton = $("#wp-submit");
    private final SelenideElement submitButton = $("#um-submit-btn");

    // Элемент, который виден только после логина
    private final SelenideElement adminMenu = $("#adminmenu");

    public LoginPage openPage() {
        //open("/wp-login.php");
        open("/loginly5r21p08qka3gpsoufk3o95/");
        return this;
    }

    @Step ("Login")
    public void login(String user, String password) {
        // 1. Заполняем поля явно
        loginField.shouldBe(visible, Duration.ofSeconds(10)).setValue(user);
        //sleep(250);
        passwordField.shouldBe(visible).setValue(password);
        //sleep(250);
        submitButton.pressEnter();
        //actions().moveToElement($("#um-submit-btn")).click().perform();
    }

    @Step("Login confirmation")
    public void shouldBeLoggedIn() {
        adminMenu.shouldBe(visible, Duration.ofSeconds(15)); // Если меню админки видно — логин прошел успешно
    }

    //Разлогинивание через UI
    /*public void logoutViaUI() {
        // 1. Наводим мышь на профиль (обычно ID #wp-admin-bar-my-account)
        $("#wp-admin-bar-my-account").hover();

        // 2. Ждем появления ссылки "Выйти" и кликаем
        // В русской локализации это "Выйти", в английской "Log Out"
        $("#wp-admin-bar-logout a").shouldBe(Condition.visible).click();

        // 3. Опционально: проверяем, что мы попали на страницу логина
        $("#loginform").shouldBe(Condition.visible);
    }*/

    //Разлогинивание быстрое - через очистку сессии
    @Step("Fast logout")
    public void fastLogout() {
        clearBrowserCookies();
        refresh();
    }

    //Разлогинивание через url
    /*public void logoutViaUrl() {
        // WordPress использует nonce для защиты от CSRF, поэтому просто /wp-login.php?action=logout может не сработать без подтверждения.
        // Самый простой способ — открыть страницу логина, она часто сбрасывает сессию, если передать параметр.
        open("/wp-login.php?action=logout");

        // Иногда WordPress просит подтвердить выход ("Вы действительно хотите выйти?")
        if ($("a[href*='action=logout']").exists()) {
            $("a[href*='action=logout']").click();
        }
    }*/



}