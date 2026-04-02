package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import com.elesinsergei.narvaonline.pages.PageEditorPage;
import com.elesinsergei.narvaonline.utils.Utils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("UI Tests")
@Feature("Page creation")
public class CreatePageUiTest extends BaseTest {

    @Test
    @Story("Page creation in admin dashboard")
    @DisplayName("Successful page creation and deletion")
    @Step("Successful page creation and deletion")
    public void shouldCreatePage() {

        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        PageEditorPage pageEditorPage = new PageEditorPage();
        Utils utils = new Utils();

        // 1. Логин
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Навигация к созданию персоны
        dashboardPage.goToNewPage();

        // 3. Создание страницы
        pageEditorPage.create("Test Page via Selenide", "Test Page content via Selenide");

        //4. Публикация страницы
        pageEditorPage.publish();

        //5. Проверка публикации страницы
        pageEditorPage.checkPublish();

        //6. Удаление страницы в корзину
        pageEditorPage.deleteCurrentPage();

        //7. Проверка удаления персоны на фронтенде
        pageEditorPage.checkDelete();

        //8. Удаление страницы из корзины
        pageEditorPage.deleteFromTrash();

        //9. Удаление тестового изображения из галереи
        utils.deleteTestImg();

        //10. Быстрое разлогинивание
        loginPage.fastLogout();

    }

}
