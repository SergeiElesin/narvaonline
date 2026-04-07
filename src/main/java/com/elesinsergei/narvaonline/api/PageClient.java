package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Page;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class PageClient {

    private static final String PAGE_ENDPOINT = "/pages/";

    //Getting page list
    @Step("Getting pages")
    public Response getPages() {
        return given()
                .auth().none()
                .when()
                .get(PAGE_ENDPOINT)
                .then()
                .extract().
                response();
    }

    //Page creation
    @Step("Page creation")
    public Response createPage(Page page) {
        return given()
                // Authorization:
                //.auth().preemptive().basic(username, appPassword)
                .contentType("application/json")
                .body(page)
                .log().all()
                .when()
                .post(PAGE_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    //Page removing (ID)
    @Step("Deleting an page via API (ID: {id})")
    public void deletePage(int id) {
        given()
                .when()
                .delete(PAGE_ENDPOINT + id + "?force=true")
                .then()
                .statusCode(200);
    }
}
