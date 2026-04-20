package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;

import java.time.Duration;
import java.util.Map;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

import org.openqa.selenium.Cookie;


/**
 * Page Object for LoginPage
 * Contains methods and data for login and logout via logon form on UI
 */
public class LoginPage {

    private final SelenideElement loginField = $("input[name^='username-']");
    private final SelenideElement passwordField = $("input[name^='user_password-']");

    private final SelenideElement submitButton = $("#um-submit-btn");

    // visible only for logged users
    private final SelenideElement adminMenu = $("#adminmenu");

    public LoginPage openPage() {
        //open("/wp-login.php");
        open("/loginly5r21p08qka3gpsoufk3o95/");
        return this;
    }

    /**
     * Login with username and password on frontend
     */
    //@Step("Login")
    public void login(String user, String password) {

        step("Login via username and password", () -> {
            // Set off Allure Listener
            SelenideLogger.removeListener("AllureSelenide");

            try {
                loginField.shouldBe(visible, Duration.ofSeconds(10)).setValue(user);
                passwordField.shouldBe(visible).setValue(password);
                submitButton.pressEnter();
            } finally {
                // Set on Allure Listener back
                SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
            }
        });
    }

//unsafe method - can see username and password in Allure Report
    /*public void login(String user, String password) {
        step("Login via username and password", () -> {
            $("#user").setValue(user);
            $("#pass").setValue(password);
            loginField.shouldBe(visible, Duration.ofSeconds(10)).setValue(user);
            passwordField.shouldBe(visible).setValue(password);
            submitButton.pressEnter();
        });
    }*/

    /**
     * Login confirmation
     */
    @Step("Login confirmation")
    public void shouldBeLoggedIn() {
        adminMenu.shouldBe(visible, Duration.ofSeconds(15)); // If the admin menu is visible, the login was successful.
    }

    /**
     * Logout via UI
     */
    @Step("Logout with logout button")
    public void logoutViaUI() {
        // 1.Hover mouse over the profile (usually ID #wp-admin-bar-my-account)
        $("#wp-admin-bar-my-account").hover();
        // 2. Wait for "Log Out" and click
        $("#wp-admin-bar-logout a").shouldBe(Condition.visible).click();
        // 3. Check: this is login page
        $("#loginform").shouldBe(Condition.visible);
    }

    /**
     * Fast logout - clean session
     */
    @Step("Fast logout")
    public void fastLogout() {
        clearBrowserCookies();
        refresh();
    }

    /**
     * Logout via URL
     */
    @Step("Logout with URL logout")
    public void logoutViaUrl() {
        open("/wp-login.php?action=logout");

        // If confirmation needed
        if ($("a[href*='action=logout']").exists()) {
            $("a[href*='action=logout']").click();
        }
    }

    /**
     * Login with cookies
     */
    public void loginWithCookies(String username, String password, String baseUrl, String loginUrl) {

        //1.GET request: Get the login page to retrieve the current nonce and form ID
        Response loginPageResponse = given()
                .noFilters() //delete Rest Assured log in Allure report for hiding credentials
                .get(loginUrl);

        //Extracting the nonce (WordPress/UM security token)
        String nonce = loginPageResponse.htmlPath().getString("**.find { it.@name == '_wpnonce' }.@value");
        //Extract the form_id (usually 3265 or a similar numeric ID in Ultimate member)
        String formId = loginPageResponse.htmlPath().getString("**.find { it.@name == 'form_id' }.@value");

        if (nonce == null || formId == null) {
            throw new IllegalStateException("Unable to extract nonce or form_id from login page!");
        }

        step("Login with cookies", () -> {
            // Set off Allure Listener
            SelenideLogger.removeListener("AllureSelenide");

            try {
                // 2.POST request: Emulating the submission of an authorization form
                Map<String, String> loginCookies = given()
                        .noFilters() //delete Rest Assured log for hiding credentials in Allure Report
                        .contentType("application/x-www-form-urlencoded")
                        .formParam("username-" + formId, username)  //Field name depends on formId
                        .formParam("user_password-" + formId, password)
                        .formParam("form_id", formId)
                        .formParam("um_request", "")
                        .formParam("_wpnonce", nonce)
                        .when()
                        .post(loginUrl)
                        .then()
                        .statusCode(302) //Wait for a redirect after a successful login
                        .extract()
                        .cookies();

                // Check: Did we get the basic authorization cookie?
                boolean hasAuthCookie = loginCookies.keySet().stream().anyMatch(name -> name.contains("wordpress_logged_in"));
                if (!hasAuthCookie) {
                    throw new RuntimeException("Authorization failed: cookie 'wordpress_logged_in' not found. Check your username/password");
                }

                //3.UI part: Passing the session to the browser
                open("/"); //First, open the domain to set the context

                loginCookies.forEach((name, value) -> {
                    Cookie cookie = new Cookie.Builder(name, value)
                            .domain(baseUrl.replaceFirst("^https://", "")) // Make sure the domain matches
                            .path("/")
                            .isSecure(true)
                            .build();
                    WebDriverRunner.getWebDriver().manage().addCookie(cookie);
                });

            } finally {
                // Set on Allure Listener back
                SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                        .screenshots(true)
                        .savePageSource(true));
            }
        });

        // 4. Open admin panel
        open("/wp-admin/");


    }

}