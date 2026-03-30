package com.elesinsergei.narvaonline.pages.Adverts;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class AdvertsAddPage {

    public AdvertsAddPage fillForm(
            String add_ad_page,
            String ad_phone,
            String ad_title,
            String ad_description,
            String ad_price,
            String add_location){

        // Если после регистрации не редирект — открываем форму явно
        open(add_ad_page);

        //Номер телефона
        $("input[name='adverts_phone']")
                .shouldBe(visible)
                .setValue(ad_phone);
        sleep(1000);

        // Заголовок объявления
        $("input[name='post_title']")
                .shouldBe(visible)
                .setValue(ad_title);
        sleep(1000);

        //Рубрика - выпадающий список
        $("#advert_category").shouldBe(visible).click();
        //$(withText("АВТОМОБИЛИ")).click();
        $$(".adverts-option-depth-0").findBy(text("АВТОМОБИЛИ")).click();
        sleep(1000);

        //Галерея - добавляем изображение
        $("input[type='file']").uploadFromClasspath("img/my_photo.png");

        // Описание (обычно textarea или wp-editor)
        // Редактор находится внутри iframe
        // Переключаемся во фрейм (по ID или индексу)
        switchTo().frame("post_content_ifr");
        // Находим body внутри фрейма и вводим текст
        $(".mce-content-body").setValue(ad_description);
        // Возвращаемся обратно к основному контенту страницы
        switchTo().defaultContent();
        sleep(1000);

        // Цена
        $("input[name='adverts_price']")
                .shouldBe(visible)
                .setValue(ad_price);
        sleep(1000);

        // Price (select или radio) — при необходимости раскомментировать
        // $("select[name='adverts_category']").selectOption("Электроника");

        //Местоположение
        $("input[name='adverts_location']")
                .shouldBe(visible)
                .setValue(add_location);
        sleep(1000);

        // Кнопка «Предпросмотр»
        $("input[name='submit']")
                .shouldBe(enabled)
                .click();
        sleep(1000);

        // Убеждаемся, что открылась страница предпросмотра
        $(".adverts-show-contact")
                .shouldBe(visible);

        //return new AdvertsAddPage();
        return this;
    }
}
