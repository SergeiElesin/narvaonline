package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.models.Post;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.hamcrest.Matchers.*;

/**
 * API test - getting list of posts, сreaton, testing, deleting post
 */
@Tag("api")
@Epic("API Tests")
@Feature("Posts API")
public class PostApiTest extends BaseTest {

    String postTitle = "API Post " + System.currentTimeMillis();
    String postContent = "API Post content " + System.currentTimeMillis();
    Integer createdPostId;

    PostClient postClient = new PostClient();

    /**
     * Getting list of posts
     */
    @Test
    @DisplayName("Getting a list of posts")
    @Description("Check that the API returns a list of posts and a status code of 200.")
    public void shouldGetPostsList() {

        Response response = postClient.getPosts();

        response.then()
                .statusCode(200) // Check request status
                .contentType("application/json") // Check, that returned JSON
                .body("size()", greaterThan(0)) // Check, that at least one post in list exists
                .body("title.rendered", hasItem(notNullValue())); // Check, that posts has titles
    }

    /**
     * 1.Post creation
     * 2.Verify on frontend by title
     * 3.Post removal
     * 4.Verify Post removal
     */
    @Test
    @Story("Post creation, test, removal via APi")
    @DisplayName("Post creation and removal via API")
    @Step("API Test: post creation and removal")
    public void postCreatedViaApiAndDeleted() {

        // 1. Creating Post
        Post postRequest = Post.builder()
                .title(postTitle)
                .content(postContent)
                .status("publish")
                .build();

        createdPostId = postClient.createPost(postRequest).path("id");

        //2. Checking post existing by title
        Response response = postClient.getPosts();
        //  Getting list of titles
        List<String> titles = response.jsonPath().getList("title.rendered");
        //  Check for title existing
        Assertions.assertTrue(titles.contains(postTitle),
                "Title '" + postTitle + "' not found in post list!");


        //3. Post deleting
        if (createdPostId != null) {
            postClient.deletePost(createdPostId);
        }

        //4. Check for full post deleting
        //Updated response
        Response responseAfterDel = postClient.getPosts();
        // Updated title list
        List<String> postTitles = responseAfterDel.jsonPath().getList("title.rendered");
        Assertions.assertFalse(postTitles.contains(postTitle),
                "Error: Post '" + postTitle + "' is still in the list!");
    }
}