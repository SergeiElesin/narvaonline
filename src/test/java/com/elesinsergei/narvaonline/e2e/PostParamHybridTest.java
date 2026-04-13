package com.elesinsergei.narvaonline.e2e;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.PostClient;
import com.elesinsergei.narvaonline.data.PostParamHybridTestData;
import com.elesinsergei.narvaonline.models.Post;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

/**
 * Parameterized test: post creation, verification, removal depends on parameters
 */

@Epic("E2E parametrized Tests")
@Feature("Parametrized post test")
public class PostParamHybridTest extends BaseTest {

    PostClient postClient = new PostClient();

    private Integer currentPostId;

    @ParameterizedTest(name = "{0} (Status: {1})")
    @ArgumentsSource(PostParamHybridTestData.class)
    @Story("Parametrized posts creation, verify via UI, post removal")
    @DisplayName("Parametrized posts test")
    void testWordPressPostLogic(String title, String status, String password, String content, int expectedCode) {

        //Creating posts

        Post postRequest = Post.builder()
                .title(title)
                .status(status)
                .content(content)
                .password(password)
                .build();

        Response response = postClient.createPost(postRequest);

        if (response.statusCode() == expectedCode  && expectedCode == 201) {
            //Get thr post ID
            currentPostId = response.path("id");

            // Get the post link
            String postLink = response.path("link");

            // Verify post via UI
            String renderedTitle = response.path("title.rendered");
            postClient.verifyPostBehavior(password, postLink, renderedTitle);

        } else {
            // Expect an error (case 3), checking only the API response code
            response
                    .then()
                    .statusCode(expectedCode);
        }
    }

    @AfterEach
    void cleanUp() {
        if (currentPostId != null) {
            postClient.deletePost(currentPostId);
        }
    }
}

