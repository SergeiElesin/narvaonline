package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.BlogEditorPage;
import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.EventEditorPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("UI Tests")
@Feature("Event creation")
public class CreateEventUiTest extends BaseTest {

    @Test
    @Story("Event creation in admin dashboard")
    @DisplayName("Successful event creation and deletion")
    @Step("Successful event creation and deletion")
    public void shouldCreateBlog() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        EventEditorPage eventEditorPage = new EventEditorPage();

        // 1. Логин
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Навигация к созданию поста в блог
        dashboardPage.goToNewEvent();

        // 3. Создание события
        eventEditorPage.createEvent("Test Event via Selenide", "Test event content via Selenide");

        //4. Публикация события
        eventEditorPage.publish();

        //5. Проверка публикации события
        eventEditorPage.checkPublish();

        //6. Удаление события в корзину
        eventEditorPage.deleteCurrentBlog();

        //7. Проверка удаления на фронтенде
        eventEditorPage.checkDelete();

        //8. Удаляем событие навсегда из корзины
        eventEditorPage.deleteFromTrash();

        //9. Быстрое разлогинивание
        loginPage.fastLogout();

    }
}
