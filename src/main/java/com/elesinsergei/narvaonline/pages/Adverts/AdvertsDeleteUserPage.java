package com.elesinsergei.narvaonline.pages.Adverts;

import com.elesinsergei.narvaonline.pages.HomePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AdvertsDeleteUserPage {

    HomePage homePage = new  HomePage();

    public AdvertsDeleteUserPage deleteUser(String my_account, String test_password){
        //open(BASE_URL + "/account/");
        open(my_account);

        //1. Нажимаем на кнопку удалить аккаунт
        $(withText("Удалить учётную запись"))
                .shouldBe(visible)
                .click();

        //2. Следующая страница - вводим пароль
        $("input[name='single_user_password']")
                .shouldBe(visible)
                .setValue(test_password);

        //3. Нажимаем Удалить аккаунт
        $("#um_account_submit_delete").shouldBe(visible).click();

        //5.Открывается главная, проверяем тайтл
        //homePage.openPage().verifyPageTitle("НАРВА ОНЛАЙН - городской портал | Здесь всё!");

        return this;
    }

}
