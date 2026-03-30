package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.Adverts.*;
import com.elesinsergei.narvaonline.pages.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static com.elesinsergei.narvaonline.data.AdvertsUiTestData.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic("UI Tests")
@Feature("Adverse adding and deleting")
public class AdvertsUiTest extends BaseTest {

    HomePage homePage = new  HomePage();
    AdvertsRegisterPage advertsRegisterPage = new AdvertsRegisterPage();
    AdvertsAddPage advertsAddPage = new AdvertsAddPage();
    AdvertsPreviewPage advertsPreviewPage = new AdvertsPreviewPage();
    AdvertsDeleteAdversePage advertsDeleteAdversePage = new AdvertsDeleteAdversePage();
    AdvertsDeleteUserPage advertsDeleteUserPage = new AdvertsDeleteUserPage();

    @Test
    @Order(1)
    @Story("User registration")
    public void registerTest(){
        advertsRegisterPage.register(TEST_USERNAME, TEST_EMAIL, TEST_PASSWORD);
        // Проверяем успешную регистрацию
        $(byTagAndText("h1", TEST_USERNAME)).shouldBe(visible);
    }

    @Test
    @Order(2)
    @Story("Form filling")
    public void addAdvert(){
        advertsAddPage.fillForm(ADD_AD_PAGE, AD_PHONE, AD_TITLE, AD_DESCRIPTION, AD_PRICE, AD_LOCATION);
        // Убеждаемся, что открылась страница предпросмотра
        $(".adverts-show-contact")
                .shouldBe(visible);
    }

    @Test
    @Order(3)
    @Story("Ad review")
    public void checkUsernameAndPrice(){
        advertsPreviewPage.previewAd(TEST_USERNAME, AD_PRICE);
    }

    @Test
    @Order(4)
    @Story("Ad publishing")
    public void publish(){
        advertsPreviewPage.publishAd();
        //Проверка наличия success-сообщения
        $(".adverts-icon-ok")
                .shouldBe(visible);
    }

    @Test
    @Order(5)
    @Story("Confirm publishing")
    public void review(){
        advertsPreviewPage.verifyAd(ADS_PAGE);
        // Объявление должно быть видно в листинге
        $$("a.advert-link-wrap")
                .findBy(attribute("title", AD_TITLE))
                .shouldBe(visible);
    }

    @Test
    @Order(6)
    @Story("Ad deleting")
    public void deleteAdv(){
        advertsDeleteAdversePage.deleteAd(MY_ADS_PAGE, AD_TITLE, MY_ADS_PAGE);
        //Проверяем отсутствие на публичной странице объявлений
        $$("a.advert-link-wrap")
                .findBy(attribute("title", AD_TITLE))
                .shouldNotBe(visible);
    }

    @Test
    @Order(7)
    @Story("User deleting")
    public void deleteUser(){
        advertsDeleteUserPage.deleteUser(MY_ACCOUNT, TEST_PASSWORD);
        //Открывается главная, проверяем тайтл
        homePage.openPage().verifyPageTitle("НАРВА ОНЛАЙН - городской портал | Здесь всё!");
    }

    //Logout after tests
    @AfterAll
    public static void fastLogout() {
        clearBrowserCookies();
        refresh();
    }
}
