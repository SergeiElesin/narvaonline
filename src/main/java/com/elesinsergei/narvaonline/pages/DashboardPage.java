package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

/**
 * DashboardPage contains data and methods, that used on wordpress dashboard
 */

public class DashboardPage {
    //Post
    private final SelenideElement postsMenu = $("#menu-posts"); // Меню "Записи"
    private final SelenideElement addNewPost = $("a[href='post-new.php']"); // Ссылка "Добавить пост

    //Organization
    private final SelenideElement postsOrgMenu = $("#menu-posts-lsvr_listing"); // Меню "Каталог"
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

    @Step("Open Create Post page")
    public void goToNewPost() {
        postsMenu.hover();
        addNewPost.click();
    }

    @Step("Open Create Organization page")
    public void goToNewOrg() {
        postsOrgMenu.hover();
        addNewOrg.click();
    }

    @Step("Open Create Blog page")
    public void goToNewBlog(){
        postsBlogMenu.hover();
        addNewBlogMenu.click();
    }

    @Step("Open Create Event page")
    public void goToNewEvent(){
        postsEventMenu.hover();
        addNewEventMenu.click();
    }

    @Step("Open Create Person page")
    public void goToNewPerson(){
        postsPersonMenu.hover();
        addNewPersonMenu.click();
    }

    @Step("Open Create Page page")
    public void goToNewPage(){
        postsPageMenu.hover();
        addNewPageMenu.click();
    }
}