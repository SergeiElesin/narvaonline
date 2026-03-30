package com.elesinsergei.narvaonline.pages.Adverts;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class AdvertsPreviewPage{

    public AdvertsPreviewPage previewAd(String test_username, String ad_price){
        //Проверяем, что имя правильное
        $(".adverts-single-author-name")
                .shouldBe(visible)
                .shouldHave(text(test_username));
        //.shouldHave(text("Narva-Online"));

        // Проверяем цену в превью
        $(".adverts-price-box").shouldHave(text(ad_price));

        //return new AdvertsPreviewPage();
        return this;
    }

    public AdvertsPreviewPage publishAd(){
        // Кнопка «Опубликовать» / «Submit» на странице предпросмотра
        //$(byTagAndText("input", "Опубликовать объявление"))
        $("input[type='submit'][value='Опубликовать объявление']")
                .shouldBe(enabled)
                .click();

        //return new AdvertsPreviewPage();
        return this;
    }

    public AdvertsPreviewPage verifyAd(String ads_page){
            // Открываем публичную страницу объявлений
            open(ads_page);

            sleep(500);

            // Объявление должно быть видно в листинге
            /*
            $$("a.advert-link-wrap")
                    .findBy(attribute("title", AD_TITLE))
                    .shouldBe(visible);
                    */

        //return new AdvertsPreviewPage();
        return this;
    }
}
