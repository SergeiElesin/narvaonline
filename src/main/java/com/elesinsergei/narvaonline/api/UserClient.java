package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.User;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API Client for users
 */
public class UserClient {

    private static final String USERS_ENDPOINT = "/users";

    /**
     * User creation
     */
    @Step("User creation")
    public Response createUser(User user) {
        return given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body(user)
                .log().all()
                .when()
                .post(USERS_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     *Getting users by slug
     */
    @Step("Getting user by slug")
    public Response getUserBySlug(String slug) {
        return given()
                .filter(new AllureRestAssured())
                .queryParam("slug", slug)
                .when()
                .get(USERS_ENDPOINT)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }

    /**
     * Post removal (ID)
     */
    @Step("User removal")
    public void deleteUser(Integer id) {
        given()
                .filter(new AllureRestAssured())
                //.filter(new io.qameta.allure.restassured.AllureRestAssured())
                .queryParam("force", true)
                .queryParam("reassign", 1)
                .when()
                .delete("/users/" + id)
                .then()
                .statusCode(200);
    }
}
