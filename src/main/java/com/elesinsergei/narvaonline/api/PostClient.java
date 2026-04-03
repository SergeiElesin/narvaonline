package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Post;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostClient {

    private static final String POSTS_ENDPOINT = "/posts";

    //Получение списка постов
    public Response getPosts() {
        return given()
                .auth().none() // Отменяет глобальную авторизацию из BaseTest для этого запроса
                .when()
                .get(POSTS_ENDPOINT)
                .then()
                .extract().
                response();
    }

    //Создание поста
    public Response createPost(Post post) {
        return given()
                // Магия авторизации здесь:
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

    //Удаление поста
    public void deletePost(Integer id) {
        given()
                .filter(new io.qameta.allure.restassured.AllureRestAssured()) // чтобы удаление тоже попало в отчет
                .when()
                .delete("/posts/" + id + "?force=true") // force=true — это важно для WP, чтобы не в корзину, а сразу
                .then()
                .statusCode(200);
    }
}
