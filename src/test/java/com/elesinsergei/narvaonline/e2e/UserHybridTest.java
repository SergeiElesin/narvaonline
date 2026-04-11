package com.elesinsergei.narvaonline.e2e;

import com.codeborne.selenide.Condition;
import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.UserClient;
import com.elesinsergei.narvaonline.models.User;
import com.elesinsergei.narvaonline.pages.LoginPage;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

/**
 * UserHybridTest contains tests for users
 */

@Epic("E2E Tests")
@Feature("Posts API")
public class UserHybridTest extends BaseTest {

    private static final String username = "1APItestUser" + System.currentTimeMillis();
    private static final String password = "7hxw!FDnj$Wk9DbFNj#F3A$b";
    private static final String email = "apitestemail@example.com";
    Integer createdUserId;

    LoginPage loginPage = new LoginPage();
    UserClient userClient = new UserClient();

    //User creation,
    @Test
    @Story("User creation, test login via UI, user removal via UI")
    @DisplayName("User creation, test login via UI, user removal via UI")
    @Step("E2E Test: User creation, login and removal")
    public void postCreatedViaApiAndDeleted() {

        // 1. Creating User via API
        User userRequest = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .user_roles(new String[]{"subscriber"})
                .status("publish")
                .build();

        createdUserId = userClient.createUser(userRequest).path("id");

        // 2. Check user exists by slug
        Response response = userClient.getUserBySlug(username);
        List<String> slugs = response.jsonPath().getList("slug");
        Assertions.assertTrue(slugs.contains(username.toLowerCase()),
                "Error: User " + username + " is not exists");

        // 3. Test login
        loginPage.openPage().login(username, password);

        $(byText(username)).shouldBe(Condition.visible);

        loginPage.fastLogout();

        // 4. Delete via ID
        if (createdUserId != null) {
            userClient.deleteUser(createdUserId);
        }

        // 5. Check user not exists by slug
        Response responseAfterDel = userClient.getUserBySlug(username);
        List<String> slugsAfterDel = responseAfterDel.jsonPath().getList("slug");
        Assertions.assertFalse(slugsAfterDel.contains(username.toLowerCase()),
                "Error: User " + username + " still exists after deletion!");
    }

}
