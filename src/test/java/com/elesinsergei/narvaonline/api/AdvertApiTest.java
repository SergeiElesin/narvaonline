package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.models.Advert;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

/**
 * API test - getting list of adverts, сreation, testing, deleting advert
 */
@Tag("api")
@Epic("API Tests")
@Feature("Adverts API")
public class AdvertApiTest extends BaseTest {

    String advertTitle = "API Advert " + System.currentTimeMillis();
    String advertContent = "API Advert content " + System.currentTimeMillis();
    Integer createdAdvertId;

    AdvertClient advertClient = new AdvertClient();

    /**
     * Getting list of adverts
     */
    @Test
    @DisplayName("Getting a list of adverts")
    @Description("Check that the API returns a list of adverts and a status code of 200.")
    public void shouldGetAdvertsList() {

        Response response = advertClient.getAdverts();

        response.then()
                .statusCode(200) // Check request status
                .contentType("application/json") // Check, that returned JSON
                .body("size()", greaterThan(0)) // Check, that at least one post in list exists
                .body("title.rendered", hasItem(notNullValue())); // Check, that posts has titles
    }

    /**
     * 1.Advert creation
     * 2.Verify creation
     * 3.Advert removal
     * 4.Verify Advert removal
     */
    @Test
    @Story("Post creation, test, removal via APi")
    @DisplayName("Post creation and removal via API")
    @Step("API Test: post creation and removal")
    public void postCreatedViaApiAndDeleted() {

        // 1. Creating Advert
        Advert advertRequest = Advert.builder()
                .title(advertTitle)
                .content(advertContent)
                .status("publish")
                .build();

        createdAdvertId = advertClient.createAdvert(advertRequest).path("id");


        //2. Checking advert existing by title
        Response response = advertClient.getAdverts();
        //  Getting list of titles
        List<String> titles = response.jsonPath().getList("title.rendered");
        //  Check for title existing
        Assertions.assertTrue(titles.contains(advertTitle),
                "Title '" + advertTitle + "' not found in post list!");


        //3. Advert deleting
        if (createdAdvertId != null) {
            advertClient.deleteAdvert(createdAdvertId);
        }

        //4. Check for full advert deleting
        //Updated response
        Response responseAfterDel = advertClient.getAdverts();
        // Updated title list
        List<String> advertTitles = responseAfterDel.jsonPath().getList("title.rendered");
        Assertions.assertFalse(advertTitles.contains(advertTitle),
                "Error: Post '" + advertTitle + "' is still in the list!");
    }
}
