package com.elesinsergei.narvaonline.e2e;

import com.codeborne.selenide.WebDriverRunner;
import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.PersonClient;
import com.elesinsergei.narvaonline.models.Person;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * PersonHybridTest contains tests for person creation and deletion
 */
@Tag("e2e")
@Epic("E2E Tests")
@Feature("Authentication, person creation, check person existing, person delete")
public class PersonHybridTest extends BaseTest {

    private final String personTitle = "E2E Person title" + System.currentTimeMillis();
    private final String personContent = "E2E Person content " + System.currentTimeMillis();
    private Integer createdPersonId;

    PersonClient personClient = new PersonClient();
    LoginPage loginPage = new LoginPage();

    /**
     * 1.Person creation via APi
     * 2.Verify created person on frontend, checking for title is visible
     */
    @Test
    @Story("Person creation via APi, see person via UI")
    @DisplayName("Successful person creation via APi")
    @Step("Create and check person")
    public void shouldSeeCreatedPersonOnFrontEnd() {

        // API: Create person (authorization within method)
        Person personRequest = Person.builder()
                .title(personTitle)
                .content(personContent)
                .status("publish")
                .build();

        createdPersonId = personClient.createPerson(personRequest).path("id");

        // UI: Checking for title is visible
        open("/people/");
        $(byText(personTitle)).should(exist);
    }

    /**
     * Clean up
     * Removal person, created in PersonHybridTest.
     * Checking for the absence of a person on frontend
     * Fast logout.
     */
    @AfterEach
    public void cleanUp() {
        // Check that the ID exists (the person was successfully created)
        if (createdPersonId != null) {
            // Delete the event
            personClient.deletePerson(createdPersonId);
            //Reload the person and force clear the cache.
            String currentUrl = WebDriverRunner.url();
            open(currentUrl + "?nocache=");
            // Checking for the absence of a person
            $(byText(personTitle)).shouldNot(exist);
            System.out.println("Cleanup: Event with ID " + createdPersonId + " was deleted.");

            loginPage.fastLogout();
        }
    }
}
