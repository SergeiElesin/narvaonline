package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

public class BlogEditorPage {

    private final String BLOG_TRASH_URL = "/wp-admin/edit.php?post_status=trash&post_type=blog";

    // В WordPress (Gutenberg) заголовки имеют класс .editor-post-title__input
    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement chooseField = $(".block-editor-default-block-appender__content");
    private final SelenideElement contentField = $(".block-editor-rich-text__editable");

    private final SelenideElement saveDraftButton = $(".editor-post-save-draft");

    // Кнопка "В корзину" в боковой панели или верхней части
    private final SelenideElement moveToTrashButton = $(".editor-post-trash");
    // Кнопка открытия настроек (шестеренка), если панель закрыта
    private final SelenideElement settingsButton = $("button[aria-label='Настройки']");
    //Блог после удаления в корзину
    private final SelenideElement blogInTrash = $(By.xpath("//tr[contains(., 'Test blog-draft via Selenide')]"));


    //Создание черновика блога
    @Step("Blog draftCreation")
    public void createDraft(String title, String content) {
        titleField.setValue(title);
        chooseField.click();
        contentField.setValue(content);
        titleField.click();
        saveDraftButton.click();
    }

    // Метод для проверки, что черновик блога сохранился
    @Step("Verify draft saved")
    public void verifyDraftSavedWithTimeout(int seconds) {
        $("[data-testid='snackbar']")
                .shouldBe(visible, Duration.ofSeconds(seconds))
                .shouldHave(text("Черновик сохранён"));
    }

    // Метод для удаления черновика блога в корзину
    @Step("Delete blog in trash")
    public void deleteCurrentBlog() {
        // Если кнопка "В корзину" не видна (панель настроек закрыта), открываем её
        if (!moveToTrashButton.isDisplayed()) {
            settingsButton.click();
        }

        //кликаем на кнопку блога
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

    //Удаление навсегда
    @Step("Delete blog forever")
    public void deleteFromTrash(){
        //Отправляем в корзину (как в предыдущем ответе)
        Selenide.open(BLOG_TRASH_URL);
        //Удаляем навсегда
        blogInTrash.hover().$(".delete").click();
    }
}
