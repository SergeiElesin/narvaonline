package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Post;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
/**
 * API Client for post
 */

public class PostClient {

    private static final String POSTS_ENDPOINT = "/posts";

    //Getting post list
    @Step("Getting posts")
    public Response getPosts() {
        return given()
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
                .contentType("application/json")
                .body(post)
                .log().all()
                .when()
                .post(POSTS_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    //Post removal via ID
    @Step("Post removal")
    public void deletePost(Integer id) {
        given()
                .filter(new io.qameta.allure.restassured.AllureRestAssured())
                .when()
                .delete("/posts/" + id + "?force=true")
                .then()
                .statusCode(200);
    }
}
