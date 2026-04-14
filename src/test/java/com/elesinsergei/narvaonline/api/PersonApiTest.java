package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.models.Person;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

/**
 * API test - getting list of person, creation, testing, deleting person
 */
@Tag("api")
@Epic("API Tests")
@Feature("Person API")
public class PersonApiTest extends BaseTest {

    String personTitle = "API Person title" + System.currentTimeMillis();
    String personContent = "API Person content " + System.currentTimeMillis();
    Integer createdPersonId;

    PersonClient personClient = new  PersonClient();

    /**
     * Getting list of persons
     */
    @Test
    @DisplayName("Getting a list of persons")
    @Description("Check that the API returns a list of persons and a status code of 200.")
    public void shouldGetPersonsList() {

        Response response = personClient.getPersons();

        response.then()
                .statusCode(200) // Check request status
                .contentType("application/json") // Check, that returned JSON
                .body("size()", greaterThan(0)) // Check, that at least one person in list exists
                .body("title.rendered", hasItem(notNullValue())); // Check, that persons has titles
    }

    /**
     * 1.Person creation
     * 2.Verify on frontend by title
     * 3.Person removal
     * 4.Verify Person removal
     */
    @Test
    @Story("Person creation, test, removal via APi")
    @DisplayName("Person creation and removal via API")
    @Step("API Test: Person creation and removal")
    public void personCreatedViaApiAndDeleted() {

        // 1. Creating Organization
        Person personRequest = Person.builder()
                .title(personTitle)
                .content(personContent)
                .status("publish")
                .build();

        createdPersonId = personClient.createPerson(personRequest).path("id");

        //2. Checking person existing by title
        Response response = personClient.getPersons();
        //  Getting list of titles
        List<String> titles = response.jsonPath().getList("title.rendered");
        //  Check for title existing
        Assertions.assertTrue(titles.contains(personTitle),
                "Title '" + personTitle + "' not found in person list!");

        //3. Person deleting
        if (createdPersonId != null) {
            personClient.deletePerson(createdPersonId);
        }

        //4. Check for full person deleting
        //Updated response
        Response responseAfterDel = personClient.getPersons();
        // Updated title list
        List<String> orgTitles = responseAfterDel.jsonPath().getList("title.rendered");
        Assertions.assertFalse(orgTitles.contains(personTitle),
                "Error: Person '" + personTitle + "' is still in the list!");
    }

}
