package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Advert;
import com.elesinsergei.narvaonline.models.Post;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API Client for Advert
 */
public class AdvertClient {

    private static final String ADVERT_ENDPOINT = "/advert";

    /**
     * Getting advert list
     */
    @Step("Getting posts")
    public Response getAdverts() {
        return given()
                .filter(new AllureRestAssured())
                .auth().none()
                .when()
                .get(ADVERT_ENDPOINT)
                .then()
                .extract().
                response();
    }

    /**
     * Advert creation
     */
    @Step("Advert creation")
    public Response createAdvert(Advert advert) {
        return given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .body(advert)
                .log().all()
                .when()
                .post(ADVERT_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Advert removal via (ID)
     */
    @Step("Post removal")
    public void deleteAdvert(Integer id) {
        given()
                .filter(new AllureRestAssured())
                .when()
                .delete("/advert/" + id + "?force=true")
                .then()
                .statusCode(200);
    }
}
