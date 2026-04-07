package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.models.Blog;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

@Epic("API Tests")
@Feature("Blog API")
public class BlogApiTest extends BaseTest {

    String blogTitle = "API Blog post title" + System.currentTimeMillis();
    String blogContent = "API Blogpost content " + System.currentTimeMillis();
    Integer createdBlogId;

    BlogClient blogClient = new BlogClient();

    //Getting list of blogposts
    @Test
    @DisplayName("Getting a list of blog posts")
    @Description("Check that the API returns a list of blog posts and a status code of 200.")
    public void shouldGetBlogPostList() {

        Response response = blogClient.getBlogPosts();

        response.then()
                .statusCode(200) // Check request status
                .contentType("application/json") // Check, that returned JSON
                .body("size()", greaterThan(0)) // Check, that at least one blog post in list exists
                .body("title.rendered", hasItem(notNullValue())); // Check, that blog posts has titles
    }

    //Blog post creation, test by title, blog post removal
    @Test
    @Story("Blog post creation, test, removal via APi")
    @DisplayName("Blog post creation and removal via API")
    @Step("API Test: Blog post creation and removal")
    public void blogCreatedViaApiAndDeleted() {

        // 1. Creating Blog post
        Blog blogRequest = Blog.builder()
                .title(blogTitle)
                .content(blogContent)
                .status("publish")
                .build();

        createdBlogId = blogClient.createBlogPost(blogRequest).path("id");

        //2. Checking blog post existing by title
        Response response = blogClient.getBlogPosts();
        //  Getting list of titles
        List<String> titles = response.jsonPath().getList("title.rendered");
        //  Check for title existing
        Assertions.assertTrue(titles.contains(blogTitle),
                "Title '" + blogTitle + "' not found in blog post list!");

        //3. Blog post deleting
        if (createdBlogId != null) {
            blogClient.deleteBlogPost(createdBlogId);
        }

        //4. Check for full blog post deleting
        //Updated response
        Response responseAfterDel = blogClient.getBlogPosts();
        // Updated title list
        List<String> orgTitles = responseAfterDel.jsonPath().getList("title.rendered");
        Assertions.assertFalse(orgTitles.contains(blogTitle),
                "Error: Blog post '" + blogTitle + "' is still in the list!");
    }

}
