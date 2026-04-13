package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.io.File;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object for OrganizationEditorPage
 * Contains methods and data used for organization creation, testing and removal
 */
public class OrganizationEditorPage {
    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement chooseField = $(".block-editor-default-block-appender__content");
    private final SelenideElement contentField = $(".block-editor-rich-text__editable");
    private final SelenideElement publishPanel = $(".editor-post-publish-panel");
    private final SelenideElement publishButton = $(".editor-post-publish-button__button");
    private final SelenideElement publishButtonFinal = $(".editor-post-publish-button");


    //Org creation via POJO
    /*@Step("Filling in organization data: {org.title}")
    public OrganizationEditorPage fillOrgData(Organization org) {
        titleField.setValue(org.getTitle());
        chooseField.click();
        contentField.setValue(org.getDescription());
        return this;
    }*/

    /**
     *Filling organization data
     */
    @Step("Filling organization data")
    public void createOrganization(String title, String content) {
        titleField.setValue(title);
        chooseField.click();
        contentField.setValue(content);
        titleField.click();

        // Set org thumbnail - file
        //$(".editor-post-featured-image__toggle").click();
        $(byText("Установить изображение записи")).click();
        // Switch to the "Upload files" tab
        $(byText("Загрузить файлы")).click();
        // Find a hidden input of the file type and send the path to the image there.
        $("input[type='file']").uploadFile(new File("src/test/resources/img/my_photo.png"));
        // "Set Post Image" button in the modal window
        $(".media-toolbar-primary .media-button-select").shouldBe(enabled).click();

    }

    /**
     * Publishing organization
     */
    @Step("Clicking the Publish button")
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
}
