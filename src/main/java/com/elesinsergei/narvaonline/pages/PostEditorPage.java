package com.elesinsergei.narvaonline.pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static com.codeborne.selenide.WebDriverConditions.url;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class PostEditorPage{
    // В WordPress (Gutenberg) заголовки имеют класс .editor-post-title__input
    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement saveDraftButton = $(".editor-post-save-draft");

    // Кнопка "В корзину" в боковой панели или верхней части
    private final SelenideElement moveToTrashButton = $(".editor-post-trash");
    // Кнопка открытия настроек (шестеренка), если панель закрыта
    private final SelenideElement settingsButton = $("button[aria-label='Настройки']");

    //Создание черновика
    public void createDraft(String title) {
        titleField.setValue(title);
        saveDraftButton.click();
    }

    // Метод для проверки, что черновик сохранился
    public void verifyDraftSavedWithTimeout(int seconds) {
        $("[data-testid='snackbar']")
                .shouldBe(visible, Duration.ofSeconds(seconds))
                .shouldHave(text("Черновик сохранён"));;
    }

    // Метод для удаления черновика в корзину
    public void deleteCurrentPost() {
        // Если кнопка "В корзину" не видна (панель настроек закрыта), открываем её
        if (!moveToTrashButton.isDisplayed()) {
            settingsButton.click();
        }

        moveToTrashButton
                .shouldBe(visible)
                .click();

        // WordPress выводит диалог подтверждения
        // Если появляется браузерное окно подтверждения:
        $(".components-modal__frame")
                .shouldBe(visible, Duration.ofSeconds(5))
                .$("button.is-primary") // Синяя кнопка подтверждения
                .shouldHave(text("В корзину"))
                .click();

        // Ждем, пока нас перенаправит в список постов
        //webdriver().shouldHave(urlContaining("edit.php"));
        webdriver().shouldHave(urlContaining("edit.php"), Duration.ofSeconds(12));

    }
}
