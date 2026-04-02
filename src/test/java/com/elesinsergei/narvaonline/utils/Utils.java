package com.elesinsergei.narvaonline.utils;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class Utils{
    public static final String PAGE_UPLOAD_URL = "/wp-admin/upload.php";

    /** picture in upload gallery */
    private final SelenideElement imgInAdmin = $(By.xpath("//tr[contains(., 'my_photo')]"));

    /**
     * Delete test image from gallery
     */
    @Step("Delete img forever")
    public void deleteTestImg(){
        open(PAGE_UPLOAD_URL);
        imgInAdmin.hover().$(".submitdelete").click();
        //Confirmation in modal (OK)
        confirm();
    };

}
