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
 * EventEditorPage contain data and methods for event creation, testing and removal
 */

public class EventEditorPage {

    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement chooseField = $(".block-editor-default-block-appender__content");
    private final SelenideElement contentField = $(".block-editor-rich-text__editable");
    private final SelenideElement publishPanel = $(".editor-post-publish-panel");
    private final SelenideElement publishButton = $(".editor-post-publish-button__button");
    private final SelenideElement publishButtonFinal = $(".editor-post-publish-button");
    private final SelenideElement datePicker= $("#lsvr_event_end_date_utc_input + span input");

    private final String EVENT_TRASH_URL = "/wp-admin/edit.php?post_status=trash&post_type=lsvr_event";
    private final String EVENT_LIST_URL = "/wp-admin/edit.php?post_type=lsvr_event";

    //Event in the admin panel
    private final SelenideElement eventInAdmin = $(By.xpath("//tr[contains(., 'Test Event via Selenide')]"));

    //Event creation
    @Step("Event Creation")
    public void createEvent(String title, String content) {
        titleField.setValue(title);
        chooseField.click();
        contentField.setValue(content);

        //Set end date
        datePicker.setValue("2030-04-15");

        titleField.click();

        // Set event thumbnail - file
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

    @Step("Check Event Publishing")
    public void checkPublish(){
        open("/sobytiya/");
        $(byText("Test Event via Selenide")).shouldBe(visible);
    }

    @Step("Delete event into trash")
    public void deleteCurrentBlog(){
        open(EVENT_LIST_URL);
        eventInAdmin.hover().$(".submitdelete").click();
    };

    @Step("Check blog deletion on frontend")
    public void checkDelete() {
        open("/sobytiya/");
        $(byText("Test Event via Selenide")).shouldNotBe(visible);
    }

    @Step("Delete event from trash")
    public void deleteFromTrash(){
        open(EVENT_TRASH_URL);
        eventInAdmin.hover().$(".delete").click();
    }


}
