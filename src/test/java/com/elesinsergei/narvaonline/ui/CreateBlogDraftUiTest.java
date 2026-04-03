package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.BlogEditorPage;
import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import com.elesinsergei.narvaonline.utils.Utils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * CreateBlogDraftUiTest - test class for draft blog post type
 */

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
        Utils utils = new Utils();

        // 1. Login
        loginPage.openPage().login(USER_NAME, PASSWORD);

        // 2. Go to blog creation
        dashboardPage.goToNewBlog();

        // 3. Draft creating
        blogEditorPage.createDraft("Test blog-draft via Selenide", "Test content via Selenide");

        //4. Check draft saving
        blogEditorPage.verifyDraftSavedWithTimeout(12);

        //5. Removal draft into trash
        blogEditorPage.deleteCurrentBlog();

        //6. Permanent removal from trash
        blogEditorPage.deleteFromTrash();

        //7. Removing a test image from the gallery
        utils.deleteTestImg();

        //8. Fast logout
        loginPage.fastLogout();
    }
}
