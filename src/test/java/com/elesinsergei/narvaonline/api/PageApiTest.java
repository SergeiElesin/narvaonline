package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.models.Page;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

/**
 * API test - getting list of pages, creation, testing, deleting pages
 */

@Epic("API Tests")
@Feature("Page API")
public class PageApiTest extends BaseTest {

    String pageTitle = "API Page title" + System.currentTimeMillis();
    String pageContent = "API Page content " + System.currentTimeMillis();
    Integer createdPageId;

    PageClient pageClient = new PageClient();

    /**
     * Getting list of pages
     */
    @Test
    @DisplayName("Getting a list of pages")
    @Description("Check that the API returns a list of pages and a status code of 200.")
    public void shouldGetPagesList() {

        Response response = pageClient.getPages();

        response.then()
                .statusCode(200) // Check request status
                .contentType("application/json") // Check, that returned JSON
                .body("size()", greaterThan(0)) // Check, that at least one page in list exists
                .body("title.rendered", hasItem(notNullValue())); // Check, that pages has titles
    }

    /**
     * 1. Page creation
     * 2. Verify on frontend by title
     * 3. Page removal
     * 4. Verify page removal
     */
    @Test
    @Story("Page creation, test, removal via APi")
    @DisplayName("Page creation and removal via API")
    @Step("API Test: Page creation and removal")
    public void pageCreatedViaApiAndDeleted() {

        // 1. Creating Page
        Page pageRequest = Page.builder()
                .title(pageTitle)
                .content(pageContent)
                .status("publish")
                .build();

        createdPageId = pageClient.createPage(pageRequest).path("id");

        //2. Checking page existing by title
        Response response = pageClient.getPages();
        //  Getting list of titles
        List<String> titles = response.jsonPath().getList("title.rendered");
        //  Check for title existing
        Assertions.assertTrue(titles.contains(pageTitle),
                "Title '" + pageTitle + "' not found in pages list!");

        //3. Page deleting
        if (createdPageId != null) {
            pageClient.deletePage(createdPageId);
        }

        //4. Check for full page deleting
        //Updated response
        Response responseAfterDel = pageClient.getPages();
        // Updated title list
        List<String> orgTitles = responseAfterDel.jsonPath().getList("title.rendered");
        Assertions.assertFalse(orgTitles.contains(pageTitle),
                "Error: Page '" + pageTitle + "' is still in the list!");
    }
}
