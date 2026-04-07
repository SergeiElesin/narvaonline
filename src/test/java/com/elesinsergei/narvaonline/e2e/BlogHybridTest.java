package com.elesinsergei.narvaonline.e2e;

import com.codeborne.selenide.WebDriverRunner;
import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.BlogClient;
import com.elesinsergei.narvaonline.models.Blog;
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
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * BlogHybridTest contains tests for Blog post creation and deletion
 */

@Epic("E2E Tests")
@Feature("Authentication, blog post creation, check blog post existing, blog post delete")
public class BlogHybridTest extends BaseTest {

    private final String BLOG_URL = "/my-blog/";
    private final String blogTitle = "E2E Blogpost title" + System.currentTimeMillis();
    private final String blogContent = "E2E Blogpost content " + System.currentTimeMillis();
    private Integer createdBlogId;

    BlogClient blogClient = new BlogClient();
    LoginPage loginPage = new LoginPage();

    @Test
    @Story("Blog creation via APi, see post via UI")
    @DisplayName("Successful blog post creation via APi")
    @Step("Create and check blog")
    public void shouldSeeCreatedBlogPostOnFrontEnd() {

        // API: Create blog (authorization within method)
        Blog blogRequest = Blog.builder()
                .title(blogTitle)
                .content(blogContent)
                .status("publish")
                .build();

        createdBlogId = blogClient.createBlogPost(blogRequest).path("id");

        // UI: Checking for title is visible
        open(BLOG_URL);
        $(byText(blogTitle)).should(exist);
    }

    @AfterEach
    public void cleanUp() {
        // Check that the ID exists (the post was successfully created)
        if (createdBlogId != null) {
            // Delete the post
            blogClient.deleteBlogPost(createdBlogId);
            //Reload the page and force clear the cache.
            String currentUrl = WebDriverRunner.url();
            open(currentUrl + "?nocache=");
            // Checking for the absence of a post
            $(byText(blogTitle)).shouldNot(exist);
            System.out.println("Cleanup: Blog with ID " + createdBlogId + " was deleted.");

            loginPage.fastLogout();
        }
    }
}
