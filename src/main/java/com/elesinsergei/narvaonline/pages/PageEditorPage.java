package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object for PageEditorPage
 * Contains data and methods for Page creating, testing and removal
 */
public class PageEditorPage {

    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement chooseField = $(".block-editor-default-block-appender__content");
    private final SelenideElement contentField = $(".block-editor-rich-text__editable");
    private final SelenideElement publishPanel = $(".editor-post-publish-panel");
    private final SelenideElement publishButton = $(".editor-post-publish-button__button");
    private final SelenideElement publishButtonFinal = $(".editor-post-publish-button");

    private final String PAGE_TRASH_URL = "/wp-admin/edit.php?post_status=trash&post_type=page";
    private final String PAGE_LIST_URL = "/wp-admin/edit.php?post_type=page";

    //Person in Admin Panel
    private final SelenideElement pageInAdmin = $(By.xpath("//tr[contains(., 'Test Page via Selenide')]"));

    /**
     * Page Creation
     */
    @Step("Page Creation")
    public void create(String title, String content) {
        titleField.setValue(title);
        chooseField.click();
        contentField.setValue(content);
        titleField.click();

/*
        // Set post thumbnail - last in gallery
        $(byText("Установить изображение страницы")).click();
        //Choose first image in list
        $(".attachment.save-ready").shouldBe(visible).click();
        //Click the Set Page Image button
        $(".media-button").shouldBe(enabled).click();
*/

        // Set post thumbnail - file
        //$(".editor-post-featured-image__toggle").click();
        $(byText("Установить изображение страницы")).click();
        // Switch to the "Upload files" tab
        $(byText("Загрузить файлы")).click();
        // Find a hidden input of the file type and send the path to the image there.
        $("input[type='file']").uploadFile(new File("src/test/resources/img/my_photo.png"));
        // "Set Post Image" button in the modal window
        $(".media-toolbar-primary .media-button-select").shouldBe(enabled).click();

    }

    /**
     * Page publishing
     */
    @Step("Publishing page")
    public void publish() {
        // 1. Click the publish button
        publishButton.click();
        // 2. Wait until the preview/publish panel becomes visible
        publishPanel.shouldBe(visible);
        // 3. Click the final "Publish" button in the sidebar
        publishButtonFinal.shouldBe(visible, enabled).click();
        // 4. Success check
        $("[data-testid='snackbar']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Страница опубликована."));;
    }

    /**
     * Check Page Publishing
     */
    @Step("Check Page Publishing")
    public void checkPublish() {
        //Open page in new window - second window (browser tab)
        $(withText("Просмотреть страницу")).click();
        //Switch to second window (browser tab)
        switchTo().window(1);
        //Test title exist
        $(byText("Test Page content via Selenide")).shouldBe(visible);
        //Close second window (browser tab)
        closeWindow();
        //Switch to first window (browser tab with opened admin panel)
        switchTo().window(0);
    }

    /**
     * Delete page into trash
     */
    @Step("Delete page into trash")
    public void deleteCurrentPage(){
        open(PAGE_LIST_URL);
        pageInAdmin.hover().$(".submitdelete").click();
    };

    /**
     * Check page deletion on frontend
     */
    @Step("Check page deletion on frontend")
    public void checkDelete() {
        open(PAGE_LIST_URL);
        //$(byText("Test Person via Selenide")).shouldNotBe(visible);
        pageInAdmin.shouldNotBe(visible);
    }

    /**
     * Delete person from trash
     */
    @Step("Delete person from trash")
    public void deleteFromTrash(){
        open(PAGE_TRASH_URL);
        pageInAdmin.hover().$(".delete").click();
    }

}
