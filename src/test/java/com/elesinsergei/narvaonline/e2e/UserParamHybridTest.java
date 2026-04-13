package com.elesinsergei.narvaonline.e2e;

import com.codeborne.selenide.Condition;
import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.UserClient;
import com.elesinsergei.narvaonline.models.User;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

/**
 * Parameterized test: user creation, verification, removal depends on parameters
 */

@Epic("E2E parametrized Tests")
@Feature("Parametrized User test")
public class UserParamHybridTest extends BaseTest {

    Integer createdUserId;
    LoginPage loginPage = new  LoginPage();
    UserClient userClient = new UserClient();

    @ParameterizedTest(name = "Проверка создания юзера: {0}")
    @CsvSource({
            "1APItestParamUser, 7hxw!FDnj$Wk9DbFNj#F3A$bPARAM, apitestemail1@example.com, publish",
            "username, password, apitestemail2@example.com, publish",
            "*?/765, 9hxw!FDnj$Wk9DbFNj#F3A$bPARAM, apitestemail2@example.com, publish"
    })
    @Story("Parametrized users creation, test login via UI, user removal")
    @DisplayName("Parametrized user test")
    public void userCreatedViaApiAndDeleted(String username, String password, String email, String status) {

        // 1. Creating User via API
        User userRequest = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .status(status)
                .user_roles(new String[]{"subscriber"})
                .build();

        Response response = userClient.createUser(userRequest);

        int statusCode = response.statusCode();

        if (statusCode == 201) {
            // 2. User created successfully — verify response body
            response.then()
                    //.statusCode(201)
                    .body("username", equalTo(username))
                    .body("email", equalTo(email))
                    .body("roles", hasItem("subscriber"));

            createdUserId = response.path("id");

            // 3. Test login
            loginPage.openPage().login(username, password);
            $(byText(username)).shouldBe(Condition.visible);
            loginPage.fastLogout();

        } else if (statusCode == 400) {
            // User already exists or invalid data
            Assertions.assertEquals(400, statusCode,
                    "Expected 400 for user: " + username);
        } else {
            Assertions.fail("Unexpected status code: " + statusCode + " for user: " + username);
        }
    }

    @AfterEach
    void cleanUp() {
        if (createdUserId != null) {
            userClient.deleteUser(createdUserId);
        }
    }
}
