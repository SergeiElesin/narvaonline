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

/**
 * CreatePageUiTest - test class for page
 */

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

        // 1. Login
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Go to page creation
        dashboardPage.goToNewPage();

        // 3. Page creation
        pageEditorPage.create("Test Page via Selenide", "Test Page content via Selenide");

        //4. Page publishing
        pageEditorPage.publish();

        //5. Check page publishing
        pageEditorPage.checkPublish();

        //6. Removal page into trash
        pageEditorPage.deleteCurrentPage();

        //7. Check of removal on frontend
        pageEditorPage.checkDelete();

        //8. Permanent removal page from trash
        pageEditorPage.deleteFromTrash();

        //9. Removing a test image from the gallery
        utils.deleteTestImg();

        //10. Fast logout
        loginPage.fastLogout();

    }

}
