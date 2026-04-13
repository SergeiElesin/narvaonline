package com.elesinsergei.narvaonline.pages;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.webdriver;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;

import java.io.File;
import java.time.Duration;

/**
 * Page Object for PostEditorPage
 * Contains methods and data for Post Draft creating, testing and removal
 */

public class PostEditorPage{

    private final String BLOG_TRASH_URL = "/wp-admin/edit.php?post_status=trash&post_type=post";

    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement chooseField = $(".block-editor-default-block-appender__content");
    private final SelenideElement contentField = $(".block-editor-rich-text__editable");
    private final SelenideElement saveDraftButton = $(".editor-post-save-draft");

    // Add to Cart button
    private final SelenideElement moveToTrashButton = $(".editor-post-trash");
    // The settings button (gear) if the panel is closed
    private final SelenideElement settingsButton = $("button[aria-label='Настройки']");
    //Пост в админке
    private final SelenideElement blogInAdmin = $(By.xpath("//tr[contains(., 'Test draft via Selenide')]"));

    //Post Draft creation

    /**
     *Post Draft creation
     */
    @Step("Post Draft creation")
    public void createDraft(String title, String content) {
        titleField.setValue(title);
        chooseField.click();
        contentField.setValue(content);
        titleField.click();

        // Set post thumbnail - file
        //$(".editor-post-featured-image__toggle").click();
        $(byText("Установить изображение записи")).click();
        // Switch to the "Upload files" tab
        $(byText("Загрузить файлы")).click();
        // Find a hidden input of the file type and send the path to the image there.
        $("input[type='file']").uploadFile(new File("src/test/resources/img/my_photo.png"));
        // "Set Post Image" button in the modal window
        $(".media-toolbar-primary .media-button-select").shouldBe(enabled).click();

        saveDraftButton.click();
    }

    /**
     *Post Draft saving verification
     */
    @Step("Post Draft saving verification")
    public void verifyDraftSavedWithTimeout(int seconds) {
        $("[data-testid='snackbar']")
                .shouldBe(visible, Duration.ofSeconds(seconds))
                .shouldHave(text("Черновик сохранён"));
    }

    /**
     * Post Draft deleting into trash
     */
    @Step("Post Draft deleting into trash")
    public void deleteCurrentPost() {
        // Если кнопка "В корзину" не видна (панель настроек закрыта), открываем её
        if (!moveToTrashButton.isDisplayed()) {
            settingsButton.click();
        }

        moveToTrashButton
                .shouldBe(visible)
                .click();

        // If WordPress displays a confirmation dialog:
        $(".components-modal__frame")
                .shouldBe(visible, Duration.ofSeconds(5))
                .$("button.is-primary") // Синяя кнопка подтверждения
                .shouldHave(text("В корзину"))
                .click();
        // Waiting to be redirected to the list of posts.
        webdriver().shouldHave(urlContaining("edit.php"), Duration.ofSeconds(12));
    }

    /**
     * Post Draft deleting from trash
     */
    @Step("Post Draft deleting from trash")
    public void deleteFromTrash(){
        //Go to the basket
        Selenide.open(BLOG_TRASH_URL);
        //Permanent removal
        blogInAdmin.hover().$(".delete").click();
    }
}
