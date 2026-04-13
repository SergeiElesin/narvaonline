package com.elesinsergei.narvaonline.e2e;

import com.codeborne.selenide.WebDriverRunner;
import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.OrganizationClient;

import com.elesinsergei.narvaonline.pages.DashboardPage;
import com.elesinsergei.narvaonline.pages.LoginPage;
import com.elesinsergei.narvaonline.pages.OrganizationEditorPage;
import com.elesinsergei.narvaonline.utils.Utils;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

/**
 * OrganizationHybridTest contains tests for organization
 */

@Epic("E2E Tests")
@Feature("Organization creation and deletion")
public class OrganizationHybridTest extends BaseTest {
    LoginPage loginPage = new LoginPage();
    DashboardPage dashboardPage = new DashboardPage();
    OrganizationEditorPage orgEditorPage = new OrganizationEditorPage();
    OrganizationClient orgClient = new OrganizationClient();
    Utils utils = new Utils();

    /**
     * 1. Login on frontend
     * 2. Transition to the creation of an organization
     * 3. Organization creation on admin panel
     * 4. Organization publication
     * 5. Get the ID of the created post from the URL
     * 6. Checking the presence of organization on front-end
     * 7. Permanently delete an organization via the API
     * 8. Removing a test image from the gallery
     * 9. Verify post removal on frontend
     */
    @Test
    @DisplayName("Creation and complete deletion of an organization")
    @Story("Organization creating and full deleting")
    void shouldCreateAndForceDeleteOrganization() {

        //This is necessary if we add post data via POJO.
        /*Organization organization = Organization.builder()
                .title("Elite Dev Studio " + System.currentTimeMillis())
                .description("Best solutions on Java")
                .build();*/

        //1. Login
        loginPage.openPage().login(USER_NAME, PASSWORD);

        //2. Transition to the creation of an organization
        dashboardPage.goToNewOrg();

        //3. Creating and publishing via POJO
        //orgEditorPage.fillOrgData(organization).publish();

        //3. Organization creation
        orgEditorPage.createOrganization("Elite Dev Studio", "Best solutions on Java");

        //4. Organization publication
        orgEditorPage.publish();

        //5. Get the ID of the created post from the URL (needed for deleting it via API)
        //int postId = Integer.parseInt(WebDriverRunner.url().replaceAll(".*post=(\\d+).*", "$1"));
        int postId = Optional.ofNullable(WebDriverRunner.url())
                .map(url -> {
                    Matcher m = Pattern.compile("post=(\\d+)").matcher(url);
                    return m.find() ? m.group(1) : null;
                })
                .map(Integer::parseInt)
                .orElseThrow(() -> new RuntimeException("Can't get postId from URL"));


        //6. Checking the presence of organization on front-end
        open("/katalog-organizatsij/");
        $(byText("Elite Dev Studio")).shouldBe(visible);
        //Getting a title from a POJO
        //$(byText(organization.getTitle())).shouldBe(visible);

        //7. Cleanup: Permanently delete an organization via the API
        orgClient.deleteOrgForce(postId);

        //8. Removing a test image from the gallery
        utils.deleteTestImg();

        //9. Final test: post is gone
        refresh();
        //Getting a title from a POJO
        //$(byText(organization.getTitle())).shouldNot(exist);
        $(byText("Elite Dev Studio")).shouldNot(exist);
    }

    /**
     * Fast logout after OrganizationHybridTest
     */
    @AfterEach
    public void cleanUp() {
        loginPage.fastLogout();
    }
}
