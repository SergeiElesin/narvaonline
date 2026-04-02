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

public class PageEditorPage {

    private final SelenideElement titleField = $(".editor-post-title__input");
    private final SelenideElement chooseField = $(".block-editor-default-block-appender__content");
    private final SelenideElement contentField = $(".block-editor-rich-text__editable");
    private final SelenideElement publishPanel = $(".editor-post-publish-panel");
    private final SelenideElement publishButton = $(".editor-post-publish-button__button");
    private final SelenideElement publishButtonFinal = $(".editor-post-publish-button");
    //private final SelenideElement datePicker= $("#lsvr_event_end_date_utc_input + span input");

    private final String PAGE_TRASH_URL = "/wp-admin/edit.php?post_status=trash&post_type=page";
    private final String PAGE_LIST_URL = "/wp-admin/edit.php?post_type=page";

    //Персона в админке
    private final SelenideElement pageInAdmin = $(By.xpath("//tr[contains(., 'Test Page via Selenide')]"));

    //Создание страницы
    @Step("Page Creation")
    public void create(String title, String content) {
        titleField.setValue(title);
        chooseField.click();
        contentField.setValue(content);
        titleField.click();


        // Устанавливаем изображение страницы - последний из галереи
        $(byText("Установить изображение страницы")).click();
        //switchTo().window(1);
        $(".attachment.save-ready").shouldBe(visible).click();
        $(".media-button").shouldBe(enabled).click();


/*
        // Устанавливаем изображение страницы - отдельный файл
        //$(".editor-post-featured-image__toggle").click();
        $(byText("Установить изображение страницы")).click();
        // Переключаемся на вкладку "Загрузить файлы"
        $(byText("Загрузить файлы")).click();
        // Находим скрытый input типа file и отправляем туда путь к картинке
        $("input[type='file']").uploadFile(new File("src/test/resources/img/my_photo.png"));
        // Кнопка "Установить изображение записи" в модальном окне
        $(".media-toolbar-primary .media-button-select").shouldBe(enabled).click();
 */

    }

    @Step("Publishing page")
    public void publish() {
        // 1. Жмем кнопку опубликовать
        publishButton.click();
        // 2. Ждем, пока панель предпросмотра/публикации станет видимой
        publishPanel.shouldBe(visible);
        // 3. Нажимаем финальную кнопку "Опубликовать" в сайдбаре
        publishButtonFinal.shouldBe(visible, enabled).click();
        // 4. Проверка успеха
        $("[data-testid='snackbar']").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text("Страница опубликована."));;
    }

    @Step("Check Page Publishing")
    public void checkPublish() {
        //Открываем страницу в новом окне
        $(withText("Просмотреть страницу")).click();
        //Переключаемся на второе окно (страницу)
        switchTo().window(1);
        //Проверяем наличие тайтла
        $(byText("Test Page content via Selenide")).shouldBe(visible);
        //закрываем второе окно
        closeWindow();
        //Переключаемся на первое окно (админку)
        switchTo().window(0);
    }

    @Step("Delete page into trash")
    public void deleteCurrentPage(){
        open(PAGE_LIST_URL);
        pageInAdmin.hover().$(".submitdelete").click();
    };

    ////////TODO
    @Step("Check page deletion on frontend")
    public void checkDelete() {
        open(PAGE_LIST_URL);
        //$(byText("Test Person via Selenide")).shouldNotBe(visible);
        pageInAdmin.shouldNotBe(visible);
    }

    @Step("Delete person from trash")
    public void deleteFromTrash(){
        open(PAGE_TRASH_URL);
        pageInAdmin.hover().$(".delete").click();
    }




}
