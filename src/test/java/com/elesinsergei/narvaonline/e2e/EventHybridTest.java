package com.elesinsergei.narvaonline.e2e;

import com.codeborne.selenide.WebDriverRunner;
import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.EventClient;
import com.elesinsergei.narvaonline.models.Event;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

/**
 * EventHybridTest contains tests for Event creation and deletion
 */
@Epic("E2E Tests")
@Feature("Authentication, event creation, check event existing, event delete")
public class EventHybridTest extends BaseTest {

    private final String eventTitle = "E2E Event title" + System.currentTimeMillis();
    private final String eventContent = "E2E Event content " + System.currentTimeMillis();
    private Integer createdEventId;

    EventClient eventClient = new EventClient();
    LoginPage loginPage = new LoginPage();

    @Test
    @Story("Event creation via APi, see post via UI")
    @DisplayName("Successful event post creation via APi")
    @Step("Create and check event")
    public void shouldSeeCreatedEventOnFrontEnd() {

        // API: Create event (authorization within method)
        Event eventRequest = Event.builder()
                .title(eventTitle)
                .content(eventContent)
                .status("publish")
                .build();

        createdEventId = eventClient.createEvent(eventRequest).path("id");

        // UI: Checking for title is visible
        //Convert title to slug
        String eventSlug = eventTitle.toLowerCase().replaceAll("\\s+", "-");
        open("/events/" + eventSlug);
        $(byText(eventTitle)).should(exist);
    }

    @AfterEach
    public void cleanUp() {
        // Check that the ID exists (the event was successfully created)
        if (createdEventId != null) {
            // Delete the event
            eventClient.deleteEvent(createdEventId);
            //Reload the pagw and force clear the cache.
            String currentUrl = WebDriverRunner.url();
            open(currentUrl + "?nocache=");
            // Checking for the absence of event
            $(byText(eventTitle)).shouldNot(exist);
            System.out.println("Cleanup: Event with ID " + createdEventId + " was deleted.");

            loginPage.fastLogout();
        }
    }

}
