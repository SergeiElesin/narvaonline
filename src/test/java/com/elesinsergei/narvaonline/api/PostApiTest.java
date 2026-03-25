package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.BaseTest;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

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
                .statusCode(200) // Проверяем статус ответа
                .contentType("application/json") // Проверяем, что вернулся JSON
                .body("size()", greaterThan(0)) // Проверяем, что в списке есть хотя бы один пост
                .body("title.rendered", hasItem(notNullValue())); // Проверяем, что у постов есть заголовки
    }
}