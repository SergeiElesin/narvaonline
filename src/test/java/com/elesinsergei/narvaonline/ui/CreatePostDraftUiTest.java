package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import com.elesinsergei.narvaonline.pages.PostEditorPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("UI Tests")
@Feature("Post creation")
public class CreatePostDraftUiTest extends BaseTest {

    @Test
    @Story("Post draft creation in admin dashboard")
    @DisplayName("Successful post draft creation")
    @Step("Successful post draft creation")
    public void shouldCreateDraftPost() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        PostEditorPage postEditorPage = new PostEditorPage();

        // 1. Логин
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Навигация к созданию поста
        dashboardPage.goToNewPost();

        // 3. Создание черновика
        postEditorPage.createDraft("Тестовый черновик через Selenide");

        //4. Проверка сохранения черновика
        postEditorPage.verifyDraftSavedWithTimeout(12);

        //5. Удаление черновика в корзину
        postEditorPage.deleteCurrentPost();

        //6. Удаление из корзины
        postEditorPage.deleteFromTrash();

        //6. Быстрое разлогинивание
        loginPage.fastLogout();
    }
}
