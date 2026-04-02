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

public class PersonEditorPage {

    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement chooseField = $(".block-editor-default-block-appender__content");
    private final SelenideElement contentField = $(".block-editor-rich-text__editable");
    private final SelenideElement publishPanel = $(".editor-post-publish-panel");
    private final SelenideElement publishButton = $(".editor-post-publish-button__button");
    private final SelenideElement publishButtonFinal = $(".editor-post-publish-button");
    //private final SelenideElement datePicker= $("#lsvr_event_end_date_utc_input + span input");

    private final String PERSON_TRASH_URL = "/wp-admin/edit.php?post_status=trash&post_type=lsvr_person";
    private final String PERSON_LIST_URL = "/wp-admin/edit.php?post_type=lsvr_person";

    //Персона в админке
    private final SelenideElement eventInAdmin = $(By.xpath("//tr[contains(., 'Test Person via Selenide')]"));

    //Создание персоны
    @Step("Person Creation")
    public void create(String title, String content) {
        titleField.setValue(title);
        chooseField.click();
        contentField.setValue(content);
        titleField.click();

        // Устанавливаем изображение записи
        //$(".editor-post-featured-image__toggle").click();
        $(byText("Установить изображение записи")).click();
        // Переключаемся на вкладку "Загрузить файлы"
        $(byText("Загрузить файлы")).click();
        // Находим скрытый input типа file и отправляем туда путь к картинке
        $("input[type='file']").uploadFile(new File("src/test/resources/img/my_photo.png"));
        // Кнопка "Установить изображение записи" в модальном окне
        $(".media-toolbar-primary .media-button-select").shouldBe(enabled).click();
    }

    @Step("Publishing event")
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

    @Step("Check Person Publishing")
    public void checkPublish() {
        open("/lyudi/");
        $(byText("Test Person via Selenide")).shouldBe(visible);
    }

    @Step("Delete person into trash")
    public void deleteCurrentPerson(){
        open(PERSON_LIST_URL);
        eventInAdmin.hover().$(".submitdelete").click();
    };

    @Step("Check person deletion on frontend")
    public void checkDelete() {
        open("/lyudi/");
        $(byText("Test Person via Selenide")).shouldNotBe(visible);
    }

    @Step("Delete person from trash")
    public void deleteFromTrash(){
        open(PERSON_TRASH_URL);
        eventInAdmin.hover().$(".delete").click();
    }

}
