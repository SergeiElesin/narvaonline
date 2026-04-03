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
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

/**
 * PostHybridTest contains tests for Posts
 */

@Epic("E2E Tests")
@Feature("Authentication, post creation, check post existing, post delete")
public class PostHybridTest extends BaseTest {

    PostClient postClient = new PostClient();
    HomePage homePage = new HomePage();
    LoginPage loginPage = new LoginPage();
    Integer createdPostId;
    String postTitle = "E2E Post " + System.currentTimeMillis();

    @Test
    @Story("Post creation via APi, see post via UI")
    @DisplayName("Successful login to the admin panel")
    @Step("Successful E2E Tests")
    public void shouldSeeCreatedPostOnFrontEnd() {

        // API: Create post (authorization within method)
        Post postRequest = Post.builder()
                .title(postTitle)
                .content("Check this out on UI!")
                .status("publish")
                .build();

        createdPostId = postClient.createPost(postRequest).path("id");

        // UI: Checking for title is visible
        homePage.openPage().verifyPostTitleIsVisible(postTitle);
    }

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