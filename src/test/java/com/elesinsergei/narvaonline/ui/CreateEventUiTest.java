package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.EventEditorPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import com.elesinsergei.narvaonline.utils.Utils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * CreateEventUiTest - test class for event post type
 */

@Epic("UI Tests")
@Feature("Event creation")
public class CreateEventUiTest extends BaseTest {

    /**
     * 1.Login via UI
     * 2.Go to event creation in admin panel
     * 3.Event creation
     * 4.Publishing event
     * 5.Check of successful event creation
     * 6.Removal event into trash
     * 7.Permanent removal event from trash
     * 8.Check of event removal on frontend
     * 9.Removing a test image from the gallery
     * 10.Fast logout
     */
    @Tag("ui")
    @Test
    @Story("Event creation in admin dashboard")
    @DisplayName("Successful event creation and deletion")
    @Step("Successful event creation and deletion")
    public void shouldCreateBlog() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        EventEditorPage eventEditorPage = new EventEditorPage();
        Utils utils = new Utils();

        // 1. Login
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Go to event creation
        dashboardPage.goToNewEvent();

        // 3. Event creating
        eventEditorPage.createEvent("Test Event via Selenide", "Test event content via Selenide");

        //4. Event publishing
        eventEditorPage.publish();

        //5. Check event publishing
        eventEditorPage.checkPublish();

        //6. Removal event into trash
        eventEditorPage.deleteCurrentBlog();

        //7. Check of removal on frontend
        eventEditorPage.checkDelete();

        //8. Permanent removal from trash
        eventEditorPage.deleteFromTrash();

        //9. Removing a test image from the gallery
        utils.deleteTestImg();

        //10. Fast logout
        loginPage.fastLogout();

    }
}
