package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.WebDriverConditions;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

/**
 * HomePage contains methods used on Home Page
 */

public class HomePage {

    public HomePage openPage() {
        open("/"); // Откроет baseUrl, который мы прописали в BaseTest
        return this;
    }

    //Check page Title
    public void verifyPageTitle(String expectedTitle) {
        webdriver().shouldHave(WebDriverConditions.title(expectedTitle), Duration.ofSeconds(10));
    }

    //Checking  visibility of the post title in the list of posts
    public void verifyPostTitleIsVisible(String expectedTitle) {
        // Search among all the headings for the one whose text matches our postTitle
        $$(".lsvr-pressville-post-grid__post-title-link")
                .findBy(text(expectedTitle))
                .shouldBe(visible);
    }
}