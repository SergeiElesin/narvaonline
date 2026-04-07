package com.elesinsergei.narvaonline.e2e;

import com.codeborne.selenide.WebDriverRunner;
import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.PageClient;
import com.elesinsergei.narvaonline.models.Page;
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
 * PageHybridTest contains tests for page creation and deletion
 */
@Epic("E2E Tests")
@Feature("Authentication, page creation, check page existing, page delete")
public class PageHybridTest extends BaseTest {

    private final String pageTitle = "E2E Page title" + System.currentTimeMillis();
    private final String pageContent = "E2E Page content " + System.currentTimeMillis();
    private Integer createdPageId;

    PageClient pageClient = new PageClient();
    LoginPage loginPage = new LoginPage();

    @Test
    @Story("Page creation via APi, see post via UI")
    @DisplayName("Successful page post creation via APi")
    @Step("Create and check page")
    public void shouldSeeCreatedPageOnFrontEnd() {

        // API: Create page (authorization within method)
        Page pageRequest = Page.builder()
                .title(pageTitle)
                .content(pageContent)
                .status("publish")
                .build();

        createdPageId = pageClient.createPage(pageRequest).path("id");

        // UI: Checking for title is visible
        //Convert title to slug
        String pageSlug = pageTitle.toLowerCase().replaceAll("\\s+", "-");
        open("/" + pageSlug);
        $(byText(pageTitle)).should(exist);
    }

    @AfterEach
    public void cleanUp() {
        // Check that the ID exists (the page was successfully created)
        if (createdPageId != null) {
            // Delete the page
            pageClient.deletePage(createdPageId);
            //Reload the page and force clear the cache.
            String currentUrl = WebDriverRunner.url();
            open(currentUrl + "?nocache=");
            // Checking for the absence of a page
            $(byText(pageTitle)).shouldNot(exist);
            System.out.println("Cleanup: Event with ID " + createdPageId + " was deleted.");

            loginPage.fastLogout();
        }
    }
}
