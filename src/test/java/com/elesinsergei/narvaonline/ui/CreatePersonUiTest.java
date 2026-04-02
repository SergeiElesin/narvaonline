package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import com.elesinsergei.narvaonline.pages.PersonEditorPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("UI Tests")
@Feature("Person creation")
public class CreatePersonUiTest extends BaseTest {

    @Test
    @Story("Person creation in admin dashboard")
    @DisplayName("Successful person creation and deletion")
    @Step("Successful person creation and deletion")
    public void shouldCreatePerson() {

        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        PersonEditorPage personEditorPage = new PersonEditorPage();

        // 1. Логин
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Навигация к созданию персоны
        dashboardPage.goToNewPerson();

        // 3. Создание персоны
        personEditorPage.create("Test Person via Selenide", "Test Person content via Selenide");

        //4. Публикация персоны
        personEditorPage.publish();

        //5. Проверка публикации персоны
        personEditorPage.checkPublish();

        //6. Удаление персоны в корзину
        personEditorPage.deleteCurrentPerson();

        //7. Проверка удаления персоны на фронтенде
        personEditorPage.checkDelete();

        //8. Удаление персоны из корзины
        personEditorPage.deleteFromTrash();

        //9. Быстрое разлогинивание
        loginPage.fastLogout();
    }
}
