package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import com.elesinsergei.narvaonline.pages.PostEditorPage;
import com.elesinsergei.narvaonline.utils.Utils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * CreatePostDraftUiTest - test class for post draft
 */
@Tag("ui")
@Epic("UI Tests")
@Feature("Post creation")
public class CreatePostDraftUiTest extends BaseTest {

    /**
     * 1.Login via UI
     * 2.Go to post draft creation in admin panel
     * 3.Post draft creation
     * 4.Check of successful post draft creation
     * 5.Removal post draft into trash
     * 6.Permanent removal post draft from trash
     * 7.Removing a test image from the gallery
     * 8.Fast logout
     */
    @Test
    @Story("Post draft creation in admin dashboard")
    @DisplayName("Successful post draft creation")
    @Step("Successful post draft creation")
    public void shouldCreateDraftPost() {
        LoginPage loginPage = new LoginPage();
        DashboardPage dashboardPage = new DashboardPage();
        PostEditorPage postEditorPage = new PostEditorPage();
        Utils utils = new Utils();

        // 1. login
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Go to post draft creation
        dashboardPage.goToNewPost();

        // 3. Post draft creation
        postEditorPage.createDraft("Test draft via Selenide", "Test content");

        //4. Check of successful post draft creation
        postEditorPage.verifyDraftSavedWithTimeout(12);

        //5. Removal post draft into trash
        postEditorPage.deleteCurrentPost();

        //6. Permanent removal post draft from trash
        postEditorPage.deleteFromTrash();

        //7. Removing a test image from the gallery
        utils.deleteTestImg();

        //8. Fast logout
        loginPage.fastLogout();
    }
}
