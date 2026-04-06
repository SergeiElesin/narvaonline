package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Post;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class PostClient {

    private static final String POSTS_ENDPOINT = "/posts";

    //Getting post list
    @Step("Getting posts")
    public Response getPosts() {
        return given()
                //Cancel global authorization in BaseTest
                .auth().none()
                .when()
                .get(POSTS_ENDPOINT)
                .then()
                .extract().
                response();
    }

    //Post creation
    @Step("Post creation")
    public Response createPost(Post post) {
        return given()
                // Authorization:
                //.auth().preemptive().basic(username, appPassword)
                .contentType("application/json") // Обязательно добавь это!
                .body(post)
                .log().all() // Выведет запрос в консоль
                .when()
                .post(POSTS_ENDPOINT)
                .then()
                .log().all() // Выведет ответ (посмотри, какой там status)
                .extract().response();
    }

    //Post removal via ID
    @Step("Post removal")
    public void deletePost(Integer id) {
        given()
                .filter(new io.qameta.allure.restassured.AllureRestAssured()) // чтобы удаление тоже попало в отчет
                .when()
                .delete("/posts/" + id + "?force=true") // force=true — это важно для WP, чтобы не в корзину, а сразу
                .then()
                .statusCode(200);
    }
}
