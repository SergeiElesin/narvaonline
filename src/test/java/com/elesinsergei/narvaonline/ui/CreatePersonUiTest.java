package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import com.elesinsergei.narvaonline.pages.PersonEditorPage;
import com.elesinsergei.narvaonline.utils.Utils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * CreatePersonUiTest - test class for person
 */

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
        Utils utils = new Utils();

        // 1. Login
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Go to person creation
        dashboardPage.goToNewPerson();

        // 3. Person creation
        personEditorPage.create("Test Person via Selenide", "Test Person content via Selenide");

        //4. Person publishing
        personEditorPage.publish();

        //5. Check publishing person
        personEditorPage.checkPublish();

        //6. Deleting person into trash
        personEditorPage.deleteCurrentPerson();

        //7. Check of person removal on frontend
        personEditorPage.checkDelete();

        //8. Permanent removal person from trash
        personEditorPage.deleteFromTrash();

        //9. Removing a test image from the gallery
        utils.deleteTestImg();

        //10. Fast logout
        loginPage.fastLogout();
    }
}
