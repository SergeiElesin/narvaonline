package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.BlogEditorPage;
import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Epic("UI Tests")
@Feature("Blog creation")
public class CreateBlogDraftUiTest extends BaseTest {

    @Test
    @Story("Blog creation in admin dashboard")
    @DisplayName("Successful blog-draft creation")
    @Step("Successful blog-draft creation")
    public void shouldCreateBlog() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        BlogEditorPage blogEditorPage = new BlogEditorPage();

        // 1. Логин
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Навигация к созданию поста в блог
        dashboardPage.goToNewBlog();

        // 3. Создание черновика
        blogEditorPage.createDraft("Test blog-draft via Selenide", "Test content via Selenide");

        //4. Проверка сохранения черновика
        blogEditorPage.verifyDraftSavedWithTimeout(12);

        //5. Удаление черновика в корзину
        blogEditorPage.deleteCurrentBlog();

        //6. Удаляем навсегда из корзины
        blogEditorPage.deleteFromTrash();

        //7. Быстрое разлогинивание
        loginPage.fastLogout();
    }
}
