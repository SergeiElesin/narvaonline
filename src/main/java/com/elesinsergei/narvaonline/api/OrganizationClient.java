package com.elesinsergei.narvaonline.api;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;


public class OrganizationClient {

    private static final String ORGANIZATIONS_ENDPOINT = "/lsvr_listing/";

    @Step("Deleting an organization via API (ID: {id})")
    public void deleteOrgForce(int id) {
        given()
                .when()
                .delete(ORGANIZATIONS_ENDPOINT + id + "?force=true")  // force=true — это важно для WP, чтобы не в корзину, а сразу
                .then()
                .statusCode(200);
    }
}
