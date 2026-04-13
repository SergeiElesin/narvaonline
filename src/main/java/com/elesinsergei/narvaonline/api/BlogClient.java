package com.elesinsergei.narvaonline.api;

import com.elesinsergei.narvaonline.models.Blog;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * API Client for blog
 */
public class BlogClient {

    private static final String BLOG_ENDPOINT = "/blog/";

    /**
     * Getting blogpost list
     */
    @Step("Getting blog posts")
    public Response getBlogPosts() {
        return given()
                .filter(new AllureRestAssured())
                .auth().none()
                .when()
                .get(BLOG_ENDPOINT)
                .then()
                .extract().
                response();
    }

    /**
     * Blog post creation
     */
    @Step("Blog Post creation")
    public Response createBlogPost(Blog blog) {
        return given()
                .filter(new AllureRestAssured())
                // Authorization:
                //.auth().preemptive().basic(username, appPassword)
                .contentType("application/json")
                .body(blog)
                .log().all()
                .when()
                .post(BLOG_ENDPOINT)
                .then()
                .log().all()
                .extract().response();
    }

    /**
     * Blogpost removing (ID)
     */
    @Step("Deleting an blogpost via API (ID: {id})")
    public void deleteBlogPost(int id) {
        given()
                .filter(new AllureRestAssured())
                .when()
                .delete(BLOG_ENDPOINT + id + "?force=true")
                .then()
                .statusCode(200);
    }
}
