package com.elesinsergei.narvaonline.pages.Adverts;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Adverts - adding advertise by filling form
 */

public class AdvertsAddPage {

    public AdvertsAddPage fillForm(
            String add_ad_page,
            String ad_phone,
            String ad_title,
            String ad_description,
            String ad_price,
            String add_location){

        // open page with form for adding advertise
        open(add_ad_page);

        //Phone number
        $("input[name='adverts_phone']")
                .shouldBe(visible)
                .setValue(ad_phone);
        sleep(1000);

        // Adverts title
        $("input[name='post_title']")
                .shouldBe(visible)
                .setValue(ad_title);
        sleep(1000);

        //Category - dropdown
        $("#advert_category").shouldBe(visible).click();
        $$(".adverts-option-depth-0").findBy(text("АВТОМОБИЛИ")).click();
        sleep(1000);

        //Gallery - adding image
        $("input[type='file']").uploadFromClasspath("img/my_photo.png");

        // Decription (textarea or  wp-editor)
        // Editor inside of iframe
        // Switch in frame ( using ID or index)
        switchTo().frame("post_content_ifr");
        // Search for body inside frame and enter text
        $(".mce-content-body").setValue(ad_description);
        // Return to default content
        switchTo().defaultContent();
        sleep(1000);

        // Price
        $("input[name='adverts_price']")
                .shouldBe(visible)
                .setValue(ad_price);
        sleep(1000);

        //Location
        $("input[name='adverts_location']")
                .shouldBe(visible)
                .setValue(add_location);
        sleep(1000);

        // Button «Preview»
        $("input[name='submit']")
                .shouldBe(enabled)
                .click();
        sleep(1000);

        // Ensure that preview page is open
        $(".adverts-show-contact")
                .shouldBe(visible);

        //return new AdvertsAddPage();
        return this;
    }
}
