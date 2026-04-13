package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Organization;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API Client for organization
 */
public class OrganizationClient {

    private static final String ORGANIZATIONS_ENDPOINT = "/lsvr_listing/";

    /**
     * Getting organizations list
     */
    @Step("Getting organizations")
    public Response getOrgs() {
        return given()
                .filter(new AllureRestAssured())
                .auth().none()
                .when()
                .get(ORGANIZATIONS_ENDPOINT)
                .then()
                .extract().
                response();
    }

    /**
     * Organization creation
     */
    @Step("Organization creation")
    public Response createOrg(Organization organization) {
        return given()
                .filter(new AllureRestAssured())
                // Authorization:
                //.auth().preemptive().basic(username, appPassword)
                .contentType("application/json")
                .body(organization)
                .log().all()
                .when()
                .post(ORGANIZATIONS_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Organization removing (ID)
     */
    @Step("Deleting an organization via API (ID: {id})")
    public void deleteOrgForce(int id) {
        given()
                .filter(new AllureRestAssured())
                .when()
                .delete(ORGANIZATIONS_ENDPOINT + id + "?force=true")
                .then()
                .statusCode(200);
    }
}
