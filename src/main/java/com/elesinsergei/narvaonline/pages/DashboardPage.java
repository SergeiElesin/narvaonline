package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

/**
 * Page object for DashboardPage
 * Contains data and methods, that used on wordpress dashboard
 */
public class DashboardPage {
    //Post
    private final SelenideElement postsMenu = $("#menu-posts"); // Menu "Posts"
    private final SelenideElement addNewPost = $("a[href='post-new.php']"); // Link "Add post"

    //Organization
    private final SelenideElement postsOrgMenu = $("#menu-posts-lsvr_listing"); // Menu "Listing"
    private final SelenideElement addNewOrg = $("a[href='post-new.php?post_type=lsvr_listing']"); // Ссылка добавить организацию"Добавить

    //Blog
    private final SelenideElement postsBlogMenu = $("#menu-posts-blog");
    private final SelenideElement addNewBlogMenu = $(byText("Добавление поста в блог"));

    //Event
    private final SelenideElement postsEventMenu = $("#menu-posts-lsvr_event");
    private final SelenideElement addNewEventMenu = $("a[href='post-new.php?post_type=lsvr_event']");

    //Person
    private final SelenideElement postsPersonMenu = $("#menu-posts-lsvr_person");
    private final SelenideElement addNewPersonMenu = $("a[href='post-new.php?post_type=lsvr_person']");

    //Page
    private final SelenideElement postsPageMenu = $("#menu-pages");
    private final SelenideElement addNewPageMenu = $("a[href='post-new.php?post_type=page']");

    /**
     * Open Create Post page
     */
    @Step("Open Create Post page")
    public void goToNewPost() {
        postsMenu.hover();
        addNewPost.click();
    }

    /**
     * Open Create Organization page
     */
    @Step("Open Create Organization page")
    public void goToNewOrg() {
        postsOrgMenu.hover();
        addNewOrg.click();
    }

    /**
     * Open Create Blog page
     */
    @Step("Open Create Blog page")
    public void goToNewBlog(){
        postsBlogMenu.hover();
        addNewBlogMenu.click();
    }

    /**
     * Open Create Event page
     */
    @Step("Open Create Event page")
    public void goToNewEvent(){
        postsEventMenu.hover();
        addNewEventMenu.click();
    }

    /**
     * Open Create Person page
     */
    @Step("Open Create Person page")
    public void goToNewPerson(){
        postsPersonMenu.hover();
        addNewPersonMenu.click();
    }

    /**
     * Open Create Page page
     */
    @Step("Open Create Page page")
    public void goToNewPage(){
        postsPageMenu.hover();
        addNewPageMenu.click();
    }
}