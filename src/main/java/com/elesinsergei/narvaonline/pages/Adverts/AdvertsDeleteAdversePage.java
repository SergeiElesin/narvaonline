package com.elesinsergei.narvaonline.pages.Adverts;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$$;

/**
 * Adverts - Delete advertise
 */

public class AdvertsDeleteAdversePage {

    public AdvertsDeleteAdversePage deleteAd(String my_ads_page, String ad_title, String ads_page){

        // Go to personal account / ad management
        open(my_ads_page);

        // Find ad in list and click “Delete”
        $$(".advert-manage-item")
                .findBy(text(ad_title))
                .$("a[title='Удалить']")
                .shouldBe(visible)
                .click();

        // Confirm the deletion in the dialog
        $(".adverts-manage-action-delete-yes").shouldHave(text("Да"))
                .shouldBe(visible)
                .click();

        //Successful deletion message
        $(".advert-manage-deleted > strong")
                .shouldHave(text(ad_title))
                .shouldBe(visible);

        //OK - click
        $(byText("OK")).click();
        sleep(500);

        // Check that the ad has disappeared from list
        $$(".advert-manage-item")
                .findBy(text(ad_title))
                .shouldNotBe(visible);

        //Check for the absence of ads on the public page
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
