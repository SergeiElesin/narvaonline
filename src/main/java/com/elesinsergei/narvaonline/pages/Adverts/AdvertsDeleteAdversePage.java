package com.elesinsergei.narvaonline.pages.Adverts;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

public class AdvertsDeleteAdversePage {

    public AdvertsDeleteAdversePage deleteAd(String my_ads_page, String ad_title, String ads_page){

        // Переходим в личный кабинет / управление объявлениями
        open(my_ads_page);

        // Находим наше объявление в списке и кликаем «Удалить»
        $$(".advert-manage-item")
                .findBy(text(ad_title))
                .$("a[title='Удалить']")
                .shouldBe(visible)
                .click();

        // Подтверждаем удаление в диалоге
        $(".adverts-manage-action-delete-yes").shouldHave(text("Да"))
                .shouldBe(visible)
                .click();

        //Сообщение об успешном удалении
        $(".advert-manage-deleted > strong")
                .shouldHave(text(ad_title))
                .shouldBe(visible);

        //OK - нажимаем
        $(byText("OK")).click();
        sleep(500);

        // Проверяем, что объявление исчезло из списка
        $$(".advert-manage-item")
                .findBy(text(ad_title))
                .shouldNotBe(visible);

        //Проверяем отсутствие на публичной странице объявлений
        open(ads_page);
        /*
        $$("a.advert-link-wrap")
                .findBy(attribute("title", ad_title))
                .shouldNotBe(visible);
        */
        //return new AdvertsDeleteAdversePage();
        return this;
    }

}
