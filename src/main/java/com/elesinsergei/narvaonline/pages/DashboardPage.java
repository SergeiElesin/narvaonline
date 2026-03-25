package com.elesinsergei.narvaonline.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement postsMenu = $("#menu-posts"); // Меню "Записи"
    private final SelenideElement addNewPost = $("a[href='post-new.php']"); // Ссылка "Добавить новую"

    public void goToNewPost() {
        postsMenu.hover();
        addNewPost.click();
    }
}