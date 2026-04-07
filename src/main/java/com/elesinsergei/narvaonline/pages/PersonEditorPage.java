package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * PersonEditorPage contains data and methods used for creation, testing and removal Person
 */

public class PersonEditorPage {

    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement chooseField = $(".block-editor-default-block-appender__content");
    private final SelenideElement contentField = $(".block-editor-rich-text__editable");
    private final SelenideElement publishPanel = $(".editor-post-publish-panel");
    private final SelenideElement publishButton = $(".editor-post-publish-button__button");
    private final SelenideElement publishButtonFinal = $(".editor-post-publish-button");

    private final String PERSON_TRASH_URL = "/wp-admin/edit.php?post_status=trash&post_type=lsvr_person";
    private final String PERSON_LIST_URL = "/wp-admin/edit.php?post_type=lsvr_person";

    //Person in admin panel
    private final SelenideElement personInAdmin = $(By.xpath("//tr[contains(., 'Test Person via Selenide')]"));

    //Person creation
    @Step("Person Creation")
    public void create(String title, String content) {
        titleField.setValue(title);
        chooseField.click();
        contentField.setValue(content);
        titleField.click();

        // person thumbnail - file
        //$(".editor-post-featured-image__toggle").click();
        $(byText("Установить изображение записи")).click();
        // Switch to the "Upload files" tab
        $(byText("Загрузить файлы")).click();
        // Find a hidden input of the file type and send the path to the image there.
        $("input[type='file']").uploadFile(new File("src/test/resources/img/my_photo.png"));
        // "Set Post Image" button in the modal window
        $(".media-toolbar-primary .media-button-select").shouldBe(enabled).click();
    }

    @Step("Publishing event")
    public void publish() {
        // 1. Click the publish button
        publishButton.click();
        // 2. Wait until the preview/publish panel becomes visible
        publishPanel.shouldBe(visible);
        // 3. Click the final "Publish" button in the sidebar
        publishButtonFinal.shouldBe(visible, enabled).click();
        // 4. Success check
        $("[data-testid='snackbar']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Запись опубликована."));;
    }

    @Step("Check Person Publishing")
    public void checkPublish() {
        open("/people/");
        $(byText("Test Person via Selenide")).shouldBe(visible);
    }

    @Step("Delete person into trash")
    public void deleteCurrentPerson(){
        open(PERSON_LIST_URL);
        personInAdmin.hover().$(".submitdelete").click();
    };

    @Step("Check person deletion on frontend")
    public void checkDelete() {
        open("/lyudi/");
        $(byText("Test Person via Selenide")).shouldNotBe(visible);
    }

    @Step("Delete person from trash")
    public void deleteFromTrash(){
        open(PERSON_TRASH_URL);
        personInAdmin.hover().$(".delete").click();
    }

}
