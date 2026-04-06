package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.models.Organization;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;

/**
 * API test - getting list of organization, creation, testing, deleting organization
 */

@Epic("API Tests")
@Feature("Organizations API")
public class OrganizationApiTest extends BaseTest {

    String orgTitle = "API Org title" + System.currentTimeMillis();
    String orgContent = "API Org content " + System.currentTimeMillis();
    String orgAddress = "API Org address " + System.currentTimeMillis();
    Integer createdOrgId;

    OrganizationClient orgClient = new OrganizationClient();

    //Getting list of posts
    @Test
    @DisplayName("Getting a list of organizations")
    @Description("Check that the API returns a list of organizations and a status code of 200.")
    public void shouldGetPostsList() {

        Response response = orgClient.getOrgs();

        response.then()
                .statusCode(200) // Check request status
                .contentType("application/json") // Check, that returned JSON
                .body("size()", greaterThan(0)) // Check, that at least one post in list exists
                .body("title.rendered", hasItem(notNullValue())); // Check, that posts has titles
    }

    //Organization creation, test by title, org removal
    @Test
    @Story("Organization creation, test, removal via APi")
    @DisplayName("Organization creation and removal via API")
    @Step("API Test: Organization creation and removal")
    public void orgCreatedViaApiAndDeleted() {

        // 1. Creating Organization
        Organization orgRequest = Organization.builder()
                .title(orgTitle)
                .description(orgContent)
                .address(orgAddress)
                .status("publish")
                .build();

        createdOrgId = orgClient.createOrg(orgRequest).path("id");

        //2. Checking org existing by title
        Response response = orgClient.getOrgs();
        //  Getting list of titles
        List<String> titles = response.jsonPath().getList("title.rendered");
        //  Check for title existing
        Assertions.assertTrue(titles.contains(orgTitle),
                "Title '" + orgTitle + "' not found in post list!");

        //3. Org deleting
        if (createdOrgId != null) {
            orgClient.deleteOrgForce(createdOrgId);
        }

        //4. Check for full org deleting
        //Updated response
        Response responseAfterDel = orgClient.getOrgs();
        // Updated title list
        List<String> orgTitles = responseAfterDel.jsonPath().getList("title.rendered");
        Assertions.assertFalse(orgTitles.contains(orgTitle),
                "Error: Organization '" + orgTitle + "' is still in the list!");

    }

}
