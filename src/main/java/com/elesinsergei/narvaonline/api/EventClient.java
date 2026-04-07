package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Event;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API Client for event
 */

public class EventClient {

    private static final String EVENT_ENDPOINT = "/lsvr_event/";

    //Getting event list
    @Step("Getting events")
    public Response getEvents() {
        return given()
                .auth().none()
                .when()
                .get(EVENT_ENDPOINT)
                .then()
                .extract().
                response();
    }

    //Event creation
    @Step("Event creation")
    public Response createEvent(Event event) {
        return given()
                // Authorization:
                //.auth().preemptive().basic(username, appPassword)
                .contentType("application/json")
                .body(event)
                .log().all()
                .when()
                .post(EVENT_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    //Event removing (ID)
    @Step("Deleting an event via API (ID: {id})")
    public void deleteEvent(int id) {
        given()
                .when()
                // force=true - permanent delete (not into trash)
                .delete(EVENT_ENDPOINT + id + "?force=true")
                .then()
                .statusCode(200);
    }
}
