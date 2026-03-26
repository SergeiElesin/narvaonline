package com.elesinsergei.narvaonline.e2e;

import com.codeborne.selenide.WebDriverRunner;
import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.OrganizationClient;

import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import com.elesinsergei.narvaonline.pages.OrganizationEditorPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

@Epic("E2E Tests")
@Feature("Organization creation and deletion")
public class OrganizationHybridTest extends BaseTest {
    LoginPage loginPage = new LoginPage();
    DashboardPage dashboardPage = new DashboardPage();
    OrganizationEditorPage orgEditorPage = new OrganizationEditorPage();
    OrganizationClient orgClient = new OrganizationClient();

    @Test
    @DisplayName("Creation and complete deletion of an organization")
    @Story("Organization creating and full deleting")
    void shouldCreateAndForceDeleteOrganization() {

        //Нужно, если данные поста добавляем через POJO
        /*Organization organization = Organization.builder()
                .title("Elite Dev Studio " + System.currentTimeMillis())
                .description("Best solutions on Java")
                .build();*/

        // 1. Логин
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Переход к созданию (через прямое URL или меню)
        dashboardPage.goToNewOrg();

        // 3. Создание и публикация через POJO
        //orgEditorPage.fillOrgData(organization).publish();

        // 3. Создание и публикация через админ-панель
        orgEditorPage.createOrganization("Elite Dev Studio", "Best solutions on Java");
        orgEditorPage.publish();

        // 4. Получаем ID созданного поста из URL (нужно для его удаления)
        int postId = Integer.parseInt(WebDriverRunner.url().replaceAll(".*post=(\\d+).*", "$1"));

        // 5. Проверка наличия организации фронтенде
        open("/katalog-organizatsij/");
        $(byText("Elite Dev Studio")).shouldBe(visible);
        //Берем тайтл из POJO
        //$(byText(organization.getTitle())).shouldBe(visible);

        // 6. Очистка: Удаляем организацию через API "навсегда"
        orgClient.deleteOrgForce(postId);
    }

    @AfterEach
    public void cleanUp() {
        // 7. Финальная проверка: пост исчез
        refresh();
        $(byText("Elite Dev Studio")).shouldNot(exist);
        //Берем тайтл из POJO
        //$(byText(organization.getTitle())).shouldNot(exist);

        loginPage.fastLogout();
    }
}
