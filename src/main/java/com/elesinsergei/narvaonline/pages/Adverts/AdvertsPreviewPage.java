package com.elesinsergei.narvaonline.pages.Adverts;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object for AdvertsPreviewPage
 * Preview added advertise
 */

public class AdvertsPreviewPage{

    /**
     * Preview Advertise
     * Check name
     * Check price
     */
    public AdvertsPreviewPage previewAd(String test_username, String ad_price){
        //Check name
        $(".adverts-single-author-name")
                .shouldBe(visible)
                .shouldHave(text(test_username));

        // Check price in preview
        $(".adverts-price-box").shouldHave(text(ad_price));
        return this;
    }

    /**
     *Publishing advertise
     */
    public AdvertsPreviewPage publishAd(){
        // Button «Submit»  on preview page
        //$(byTagAndText("input", "Опубликовать объявление"))
        $("input[type='submit'][value='Опубликовать объявление']")
                .shouldBe(enabled)
                .click();

        //Check for success-message
        /*$(".adverts-icon-ok")
                .shouldBe(visible);*/

        //return new AdvertsPreviewPage();
        return this;
    }

    /**
     * Open advertise page
     * Verify advertise publishing
     */
    public AdvertsPreviewPage verifyAd(String ads_page){
            // Open ads public page
            open(ads_page);

            sleep(500);

        // Advertise has to be in listing
            /*
            $$("a.advert-link-wrap")
                    .findBy(attribute("title", AD_TITLE))
                    .shouldBe(visible);
                    */

        //return new AdvertsPreviewPage();
        return this;
    }
}
