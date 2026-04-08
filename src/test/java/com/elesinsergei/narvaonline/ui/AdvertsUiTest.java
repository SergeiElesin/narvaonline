package com.elesinsergei.narvaonline.ui;

import com.elesinsergei.narvaonline.BaseTest;
import com.elesinsergei.narvaonline.pages.Adverts.*;
import com.elesinsergei.narvaonline.pages.HomePage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byTagAndText;
import static com.codeborne.selenide.Selenide.*;
import static com.elesinsergei.narvaonline.data.AdvertsUiTestData.*;


/**
 * Adverts full cycle testing of WPAdverts functionality: register user, adding advertise, preview, delete ads, delete user
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Epic("UI Tests")
@Feature("Adverse adding and deleting ")
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
    @DisplayName("1. Register User")
    public void registerTest(){
        advertsRegisterPage.register(TEST_USERNAME, TEST_EMAIL, TEST_PASSWORD);
        // Check for successful registration
        $(byTagAndText("h1", TEST_USERNAME)).shouldBe(visible);
        sleep(3000);
    }

    @Test
    @Order(2)
    @Story("Form filling")
    @DisplayName("2. Filling Ad Form")
    public void addAdvert(){
        advertsAddPage.fillForm(ADD_AD_PAGE, AD_PHONE, AD_TITLE, AD_DESCRIPTION, AD_PRICE, AD_LOCATION);
        // Ensure, that preview page opens
        $(".adverts-show-contact")
                .shouldBe(visible);
    }

    @Test
    @Order(3)
    @Story("Ad review")
    @DisplayName("3. Review Ad")
    public void checkUsernameAndPrice(){
        advertsPreviewPage.previewAd(TEST_USERNAME, AD_PRICE);
        // Check price in preview
        //$(".adverts-price-box").shouldHave(text(AD_PRICE));
    }

    @Test
    @Order(4)
    @Story("Ad publishing")
    @DisplayName("4. Publishing Ad")
    public void publish(){
        advertsPreviewPage.publishAd();
        //Check for success-message
        $(".adverts-icon-ok")
                .shouldBe(visible);
    }

    @Test
    @Order(5)
    @Story("Confirm publishing")
    @DisplayName("5. Confirm Ad Publishing")
    public void review(){
        advertsPreviewPage.verifyAd(ADS_PAGE);
        // Advertise has to be in listing
        $$("a.advert-link-wrap")
                .findBy(attribute("title", AD_TITLE))
                .shouldBe(visible);
    }

    @Test
    @Order(6)
    @Story("Ad deleting")
    @DisplayName("6. Delete Ad")
    public void deleteAdv(){
        advertsDeleteAdversePage.deleteAd(MY_ADS_PAGE, AD_TITLE, MY_ADS_PAGE);
        //Check, that ads is absent on public ads page
        $$("a.advert-link-wrap")
                .findBy(attribute("title", AD_TITLE))
                .shouldNotBe(visible);
    }

    @Test
    @Order(7)
    @Story("User deleting")
    @DisplayName("7. Delete User")
    public void deleteUser(){
        advertsDeleteUserPage.deleteUser(MY_ACCOUNT, TEST_PASSWORD);
        //Open main page, check title
        homePage.openPage().verifyPageTitle("НАРВА ОНЛАЙН - городской портал | Здесь всё!");
    }

    //Logout after tests
    @AfterAll
    public static void fastLogout() {
        clearBrowserCookies();
        refresh();
    }
}
