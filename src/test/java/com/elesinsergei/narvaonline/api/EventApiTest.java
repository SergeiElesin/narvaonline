package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.models.Event;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

/**
 * API test - getting list of event, creation, testing, deleting event
 */

@Epic("API Tests")
@Feature("Event API")
public class EventApiTest extends BaseTest {

    String eventTitle = "API Event title " + System.currentTimeMillis();
    String eventContent = "API Event content " + System.currentTimeMillis();
    Integer createdEventId;

    EventClient eventClient = new EventClient();

    //Getting list of events
    @Test
    @DisplayName("Getting a list of events")
    @Description("Check that the API returns a list of events and a status code of 200.")
    public void shouldGetEventList() {

        Response response = eventClient.getEvents();

        response.then()
                .statusCode(200) // Check request status
                .contentType("application/json") // Check, that returned JSON
                .body("size()", greaterThan(0)) // Check, that at least one post in list exists
                .body("title.rendered", hasItem(notNullValue())); // Check, that posts has titles
    }

    //Event creation, test by title, event removal
    @Test
    @Story("Event creation, test, removal via APi")
    @DisplayName("Event creation and removal via API")
    @Step("API Test: Event creation and removal")
    public void eventCreatedViaApiAndDeleted() {

        // 1. Creating Event
        Event eventRequest = Event.builder()
                .title(eventTitle)
                .content(eventContent)
                .status("publish")
                .build();

        createdEventId = eventClient.createEvent(eventRequest).path("id");

        //2. Checking event existing by title
        Response response = eventClient.getEvents();
        //  Getting list of titles
        List<String> titles = response.jsonPath().getList("title.rendered");
        //  Check for title existing
        Assertions.assertTrue(titles.contains(eventTitle),
                "Title '" + eventTitle + "' not found in event list!");

        //3. Event deleting
        if (createdEventId != null) {
            eventClient.deleteEvent(createdEventId);
        }

        //4. Check for full event deleting
        //Updated response
        Response responseAfterDel = eventClient.getEvents();
        // Updated title list
        List<String> orgTitles = responseAfterDel.jsonPath().getList("title.rendered");
        Assertions.assertFalse(orgTitles.contains(eventTitle),
                "Error: Event '" + eventTitle + "' is still in the list!");
    }

}
