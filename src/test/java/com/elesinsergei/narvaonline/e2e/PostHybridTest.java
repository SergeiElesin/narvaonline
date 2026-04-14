package com.elesinsergei.narvaonline.e2e;

import com.codeborne.selenide.WebDriverRunner;
import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.PostClient;
import com.elesinsergei.narvaonline.models.Post;
import com.elesinsergei.narvaonline.pages.HomePage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

/**
 * PostHybridTest contains tests for Posts
 */
@Tag("e2e")
@Epic("E2E Tests")
@Feature("Authentication, post creation, check post existing, post delete")
public class PostHybridTest extends BaseTest {

    private final String postTitle = "E2E Post title" + System.currentTimeMillis();
    private final String blogContent = "E2E Blogpost content " + System.currentTimeMillis();
    Integer createdPostId;

    PostClient postClient = new PostClient();
    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();

    /**
     *1.Post creation via API
     * 2.Verify post creation on frontend, checking for title is visible
     */

    @Test
    @Story("Post creation via APi, see post via UI")
    @DisplayName("Successful post creation via APi")
    @Step("Create and check post")
    public void shouldSeeCreatedPostOnFrontEnd() {

        // API: Create post (authorization within method)
        Post postRequest = Post.builder()
                .title(postTitle)
                .content(blogContent)
                .status("publish")
                .build();

        createdPostId = postClient.createPost(postRequest).path("id");

        // UI: Checking for title is visible
        homePage.openPage().verifyPostTitleIsVisible(postTitle);
    }

    /**
     * Clean up
     * Removal post, created in PostHybridTest.
     * Checking for the absence of a post on frontend
     * Fast logout.
     */
    @AfterEach
    public void cleanUp() {
        // Check that the ID exists (the post was successfully created)
        if (createdPostId != null) {
            // Delete the post
            postClient.deletePost(createdPostId);
            //Reload the page and force clear the cache.
            String currentUrl = WebDriverRunner.url();
            //open(currentUrl + "?nocache=" + System.currentTimeMillis());
            open(currentUrl + "?nocache=");
            // Checking for the absence of a post
            $(byText(postTitle)).shouldNot(exist);
            System.out.println("Cleanup: Post with ID " + createdPostId + " was deleted.");

            loginPage.fastLogout();
        }
    }
}