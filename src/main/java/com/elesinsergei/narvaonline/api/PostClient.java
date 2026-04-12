package com.elesinsergei.narvaonline.api;

import com.codeborne.selenide.Condition;
import com.elesinsergei.narvaonline.models.Post;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
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
                .filter(new AllureRestAssured())
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
                .filter(new AllureRestAssured())
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
                .filter(new AllureRestAssured())
                .when()
                .delete("/posts/" + id + "?force=true")
                .then()
                .statusCode(200);
    }

    /**
     * for PostParamHybridTest
     */
    @Step("API: Parametrized Post creation")
    public Response createParamPost(String title, String status, String password, String content) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(String.format(
                        "{\"title\":\"%s\", \"status\":\"%s\", \"password\":\"%s\", \"content\":\"%s\"}",
                        title, status, password, content
                ))
                .when()
                .post("/posts");
    }

    @Step("UI: Test that post is visible")
    public void verifyPostBehavior(String title, String password, String postLink) {
        open(postLink);
        if (password != null && !password.isEmpty()) {
            $(byText("Защищено: " + title)).shouldBe(Condition.visible);
            $(".post-password-form").shouldBe(Condition.visible);
        } else {
            $(byText(title)).shouldBe(Condition.visible);
        }
    }
}
