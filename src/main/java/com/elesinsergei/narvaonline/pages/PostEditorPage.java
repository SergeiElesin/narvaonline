package com.elesinsergei.narvaonline.pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class PostEditorPage{

    private final String BLOG_TRASH_URL = "/wp-admin/edit.php?post_status=trash&post_type=post";

    // В WordPress (Gutenberg) заголовки имеют класс .editor-post-title__input
    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement saveDraftButton = $(".editor-post-save-draft");

    // Кнопка "В корзину" в боковой панели или верхней части
    private final SelenideElement moveToTrashButton = $(".editor-post-trash");
    // Кнопка открытия настроек (шестеренка), если панель закрыта
    private final SelenideElement settingsButton = $("button[aria-label='Настройки']");
    //Пост после удаления в корзину
    private final SelenideElement blogInTrash = $(By.xpath("//tr[contains(., 'Тестовый черновик через Selenide')]"));

    //Создание черновика
    @Step("Post Draft creation")
    public void createDraft(String title) {
        titleField.setValue(title);
        saveDraftButton.click();
    }

    // Метод для проверки, что черновик сохранился
    @Step("Post Draft saving verification")
    public void verifyDraftSavedWithTimeout(int seconds) {
        $("[data-testid='snackbar']")
                .shouldBe(visible, Duration.ofSeconds(seconds))
                .shouldHave(text("Черновик сохранён"));
    }

    // Метод для удаления черновика в корзину
    @Step("Post Draft deleting into trash")
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
        webdriver().shouldHave(urlContaining("edit.php"), Duration.ofSeconds(12));
    }

    @Step("Post Draft deleting from trash")
    public void deleteFromTrash(){
        //Отправляем в корзину (как в предыдущем ответе)
        Selenide.open(BLOG_TRASH_URL);
        //Удаляем навсегда
        blogInTrash.hover().$(".delete").click();
    }
}
