package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Person;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

/**
 * API Client for person
 */

public class PersonClient {

    private static final String PERSON_ENDPOINT = "/lsvr_person/";

    //Getting person list
    @Step("Getting persons")
    public Response getPersons() {
        return given()
                .auth().none()
                .when()
                .get(PERSON_ENDPOINT)
                .then()
                .extract().
                response();
    }

    //Person creation
    @Step("Person creation")
    public Response createPerson(Person person) {
        return given()
                // Authorization:
                //.auth().preemptive().basic(username, appPassword)
                .contentType("application/json")
                .body(person)
                .log().all()
                .when()
                .post(PERSON_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    //Person removing (ID)
    @Step("Deleting an person via API (ID: {id})")
    public void deletePerson(int id) {
        given()
                .when()
                // force=true - permanent delete (not into trash)
                .delete(PERSON_ENDPOINT + id + "?force=true")
                .then()
                .statusCode(200);
    }
}
