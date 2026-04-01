package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement postsMenu = $("#menu-posts"); // Меню "Записи"
    private final SelenideElement addNewPost = $("a[href='post-new.php']"); // Ссылка "Добавить пост

    private final SelenideElement postsOrgMenu = $("#menu-posts-lsvr_listing"); // Меню "Каталог"
    private final SelenideElement addNewOrg = $("a[href='post-new.php?post_type=lsvr_listing']"); // Ссылка добавить организацию"Добавить

    private final SelenideElement postsBlogMenu = $("#menu-posts-blog");
    private final SelenideElement addNewBlogMenu = $(byText("Добавление поста в блог"));

    public void goToNewPost() {
        postsMenu.hover();
        addNewPost.click();
    }

    public void goToNewOrg() {
        postsOrgMenu.hover();
        addNewOrg.click();
    }

    public void goToNewBlog(){
        postsBlogMenu.hover();
        addNewBlogMenu.click();
    }
}