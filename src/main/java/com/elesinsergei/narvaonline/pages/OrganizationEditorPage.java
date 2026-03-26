package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class OrganizationEditorPage {
    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement chooseField = $(".block-editor-default-block-appender__content");
    private final SelenideElement contentField = $(".block-editor-rich-text__editable");
    private final SelenideElement publishPanel = $(".editor-post-publish-panel");
    private final SelenideElement publishButton = $(".editor-post-publish-button__button");
    private final SelenideElement publishButtonFinal = $(".editor-post-publish-button");


    //Создание организации через POJO
    /*@Step("Filling in organization data: {org.title}")
    public OrganizationEditorPage fillOrgData(Organization org) {
        titleField.setValue(org.getTitle());
        chooseField.click();
        contentField.setValue(org.getDescription());
        return this;
    }*/

    //Создание организации через админ-панель
    @Step("Filling in organization data")
    public void createOrganization(String title, String content) {
        titleField.setValue(title);
        chooseField.click();
        contentField.setValue(content);
    }

    @Step("Clicking the Publish button")
    public void publish() {
        // 1. Жмем кнопку опубликовать
        publishButton.click();
        // 2. Ждем, пока панель предпросмотра/публикации станет видимой
        publishPanel.shouldBe(visible);
        // 3. Нажимаем финальную кнопку "Опубликовать" в сайдбаре
        publishButtonFinal.shouldBe(visible, enabled).click();
        // 4. Проверка успеха
        $("[data-testid='snackbar']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Запись опубликована."));;
    }
}
