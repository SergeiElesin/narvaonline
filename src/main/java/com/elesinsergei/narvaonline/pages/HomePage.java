package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverConditions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class HomePage {

    // Используем селектор для заголовка (обычно это h1 или название сайта)
    //private final SelenideElement mainHeader = $("h1.entry-title");

    // Селектор для заголовка поста на главной или странице поста
   //private SelenideElement postTitleHeader = $(".lsvr-pressville-post-grid__post-title-link");

    public HomePage openPage() {
        open("/"); // Откроет baseUrl, который мы прописали в BaseTest
        return this;
    }

    //проверяем тайтл страницы
    public void verifyPageTitle(String expectedTitle) {
        Selenide.webdriver().shouldHave(WebDriverConditions.title(expectedTitle), Duration.ofSeconds(10));
    }

    //проверяем видимость заголовка поста в списке постов
    public void verifyPostTitleIsVisible(String expectedTitle) {
        // Ищем среди всех заголовков тот, у которого текст совпадает с нашим postTitle
        $$(".lsvr-pressville-post-grid__post-title-link")
                .findBy(text(expectedTitle))
                .shouldBe(visible);
    }
}