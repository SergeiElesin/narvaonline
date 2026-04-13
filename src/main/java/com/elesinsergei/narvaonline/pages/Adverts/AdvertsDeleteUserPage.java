package com.elesinsergei.narvaonline.pages.Adverts;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * Pge Object for AdvertsDeleteUserPage
 * Delete user
 */
public class AdvertsDeleteUserPage {

    /**
     * Delete user
     */
    public AdvertsDeleteUserPage deleteUser(String my_account, String test_password){
        open(my_account);

        //1. Click on the delete account button
        $(withText("Удалить учётную запись"))
                .shouldBe(visible)
                .click();

        //2. Next page - enter your password
        $("input[name='single_user_password']")
                .shouldBe(visible)
                .setValue(test_password);

        //3. Click Delete Account
        $("#um_account_submit_delete").shouldBe(visible).click();

        //4.The main page opens, check the title
        //homePage.openPage().verifyPageTitle("НАРВА ОНЛАЙН - городской портал | Здесь всё!");

        return this;
    }

}
