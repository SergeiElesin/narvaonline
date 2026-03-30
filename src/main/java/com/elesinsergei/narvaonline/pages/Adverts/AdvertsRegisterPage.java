package com.elesinsergei.narvaonline.pages.Adverts;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AdvertsRegisterPage {

    public AdvertsRegisterPage register(String test_username, String test_email, String test_password){
        open("/");
        $("a[href*='/obyavleniya/']").hover();
        $("a.header-menu-primary__item-link--level-1[href*=\"dobavit-obyavleniye\"]").click();

        // Переключаемся на таб «Регистрация» (если есть переключатель)
        $("a[href*=\"action=register\"]")
                .shouldBe(visible)
                .click();

        // Переходим на форму регистрации
        $("a[href$=\"/register/\"]")
                .shouldBe(enabled)
                .click();

        // Заполняем форму регистрации WP Adverts
        $("input[name^='user_login-']")
                .shouldBe(visible)
                .setValue(test_username);

        $("input[name^='user_email-']")
                .shouldBe(visible)
                .setValue(test_email);

        $("input[name^='user_password-']")
                .shouldBe(visible)
                .setValue(test_password);

        $("input[name^='confirm_user_password-']")
                .shouldBe(visible)
                .setValue(test_password);

        // Отправляем форму регистрации
        $("#um-submit-btn")
                .shouldBe(enabled)
                .click();

        // Проверяем успешную регистрацию —
        //$(byTagAndText("h1", test_username)).shouldBe(visible);

        //return new  AdvertsRegisterPage();
        return this;
    }

}
