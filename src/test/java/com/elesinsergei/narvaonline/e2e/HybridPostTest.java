package com.elesinsergei.narvaonline.e2e;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.api.PostClient;
import com.elesinsergei.narvaonline.models.Post;
import com.elesinsergei.narvaonline.pages.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@Epic("E2E Tests")
@Feature("Authentication, post creation, check post existing, post delete")
public class HybridPostTest extends BaseTest {

    PostClient postClient = new PostClient();
    HomePage homePage = new HomePage();
    Integer createdPostId;

    @Test
    @Story("Post creation via APi, see post via UI")
    @DisplayName("Successful login to the admin panel")
    @Step("Successful E2E Tests")
    public void shouldSeeCreatedPostOnFrontEnd() {
        String postTitle = "E2E Post " + System.currentTimeMillis();

        // API: Создаем пост (авторизация произойдет внутри метода)
        Post postRequest = Post.builder()
                .title(postTitle)
                .content("Check this out on UI!")
                .status("publish")
                .build();

        createdPostId = postClient.createPost(postRequest).path("id");

        // UI: Проверка на наличие тайтла
        homePage.openPage().verifyPostTitleIsVisible(postTitle);
    }

    //тест проверки ответа сервера на залогинивание HTTPS

    //тест проверки ответа сервера на залогинивание HTTPS - обновленный
    /*@Test
    public void debugAuth() {
        given()
                .when()
                .get("/users/me") // Путь склеится с baseURI автоматически
                .then()
                .log().all()
                .statusCode(200);
    }*/


    @AfterEach
    public void cleanUp() {
        // Проверяем, что ID существует (пост был успешно создан)
        if (createdPostId != null) {
            // Вызываем метод без лишних параметров, так как логин/пароль уже внутри клиента
            postClient.deletePost(createdPostId);
            System.out.println("Cleanup: Post with ID " + createdPostId + " was deleted.");
        }
    }
}