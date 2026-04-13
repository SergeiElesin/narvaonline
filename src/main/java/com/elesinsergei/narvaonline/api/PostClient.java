package com.elesinsergei.narvaonline.api;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.elesinsergei.narvaonline.models.Post;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringEscapeUtils;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.NoAlertPresentException;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

/**
 * API Client for post
 */

public class PostClient {

    private static final String POSTS_ENDPOINT = "/posts";

    //Getting post list
    @Step("Getting posts")
    public Response getPosts() {
        return given()
                .filter(new AllureRestAssured())
                .auth().none()
                .when()
                .get(POSTS_ENDPOINT)
                .then()
                .extract().
                response();
    }

    //Post creation
    @Step("Post creation")
    public Response createPost(Post post) {
        return given()
                .filter(new AllureRestAssured())
                // Authorization:
                //.auth().preemptive().basic(username, appPassword)
                .contentType("application/json")
                .body(post)
                .log().all()
                .when()
                .post(POSTS_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    //Post removal via ID
    @Step("Post removal")
    public void deletePost(Integer id) {
        given()
                .filter(new AllureRestAssured())
                .when()
                .delete("/posts/" + id + "?force=true")
                .then()
                .statusCode(200);
    }

    /**
     * for PostParamHybridTest
     */
    @Step("UI: Test that post is published")
    public void verifyPostBehavior(String password, String postLink, String renderedTitle) {
        open(postLink);

        //check for alarm
        try {
            String alertText = WebDriverRunner.getWebDriver().switchTo().alert().getText();
            WebDriverRunner.getWebDriver().switchTo().alert().accept();
            Assertions.fail("XSS vulnerability detected! Alert fired with text: " + alertText);
        } catch (NoAlertPresentException ignored) {

        }

        String displayedTitle = StringEscapeUtils.unescapeHtml4(renderedTitle);

        if (password != null && !password.isEmpty()) {
            if (!displayedTitle.isEmpty()) {
                assertTextPresentOnPage("Защищено: " + displayedTitle);
            }
            $(".post-password-form").shouldBe(Condition.visible);
        } else {
            if (!displayedTitle.isEmpty()) {
                assertTextPresentOnPage(displayedTitle);
            }
        }
    }


    private void assertTextPresentOnPage(String text) {
        // Normalize multiple spaces into one
        String normalizedText = text.replaceAll("\\s+", " ").trim();

        Boolean found = Selenide.executeJavaScript(
                "var pageText = document.body.innerText.replace(/\\s+/g, ' ');" +
                "return pageText.includes(arguments[0]);",
                normalizedText
        );
        Assertions.assertThat(found)
                .as("Expected text on page: [" + normalizedText + "]")
                .isTrue();
    }
}
