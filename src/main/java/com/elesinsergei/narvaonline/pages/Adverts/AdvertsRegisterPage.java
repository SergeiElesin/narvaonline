package com.elesinsergei.narvaonline.pages.Adverts;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object for  AdvertsRegisterPage
 * Register user on frontend
 */

public class AdvertsRegisterPage {

    /**
     *Register User
     */
    public AdvertsRegisterPage register(String test_username, String test_email, String test_password){
        open("/");
        $("a[href*='/obyavleniya/']").hover();
        $("a.header-menu-primary__item-link--level-1[href*=\"dobavit-obyavleniye\"]").click();

        // Switch to tab «Registration» (if toggle exist)
        $("a[href*=\"action=register\"]")
                .shouldBe(visible)
                .click();

        // Go to registration form
        $("a[href$=\"/register/\"]")
                .shouldBe(enabled)
                .click();

        // Filling Ultimate Member plugin registration form
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

        // Sending registration data
        $("#um-submit-btn")
                .shouldBe(enabled)
                .click();

        // Check for successful registration
        //$(byTagAndText("h1", test_username)).shouldBe(visible);

        return this;
    }

}
