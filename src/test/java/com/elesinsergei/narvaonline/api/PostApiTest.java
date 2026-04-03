package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.BaseTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

/**
 * API test - getting list of posts
 */

@Epic("API Tests")
@Feature("Posts API")
@Owner("Sergei Elesin")
public class PostApiTest extends BaseTest {

    PostClient postClient = new PostClient();

    @Test
    @DisplayName("Getting a list of posts")
    @Description("Check that the API returns a list of posts and a status code of 200.")
    @Step("Checking for posts")
    public void shouldGetPostsList() {

        Response response = postClient.getPosts();

        response.then()
                .statusCode(200) // Check request status
                .contentType("application/json") // Chech, that returned JSON
                .body("size()", greaterThan(0)) // Check, that at least one post in list exists
                .body("title.rendered", hasItem(notNullValue())); // Check, that posts has titles
    }
}