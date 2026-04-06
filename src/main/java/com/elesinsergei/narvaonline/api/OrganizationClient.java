package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Organization;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrganizationClient {

    private static final String ORGANIZATIONS_ENDPOINT = "/lsvr_listing/";

    //Getting orgs list
    @Step("Getting organizations")
    public Response getOrgs() {
        return given()
                //Cancel global authorization in BaseTest
                .auth().none()
                .when()
                .get(ORGANIZATIONS_ENDPOINT)
                .then()
                .extract().
                response();
    }

    //Organization creation
    @Step("Organization creation")
    public Response createOrg(Organization organization) {
        return given()
                // Authorization:
                //.auth().preemptive().basic(username, appPassword)
                .contentType("application/json") // Обязательно добавь это!
                .body(organization)
                .log().all() // Выведет запрос в консоль
                .when()
                .post(ORGANIZATIONS_ENDPOINT)
                .then()
                .log().all() // Выведет ответ (посмотри, какой там status)
                .extract().response();
    }

    //Organization removing (ID)
    @Step("Deleting an organization via API (ID: {id})")
    public void deleteOrgForce(int id) {
        given()
                .when()
                // force=true - permanent delete (not into trash)
                .delete(ORGANIZATIONS_ENDPOINT + id + "?force=true")
                .then()
                .statusCode(200);
    }
}
