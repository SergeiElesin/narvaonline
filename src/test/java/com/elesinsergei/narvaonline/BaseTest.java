package com.elesinsergei.narvaonline;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.elesinsergei.narvaonline.config.ApplicationConfig;
import com.elesinsergei.narvaonline.config.AuthConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;


public class BaseTest {

    static ApplicationConfig applicationConfig = ConfigFactory.create(ApplicationConfig.class);
    public static final String BASE_URL = applicationConfig.baseUrl();
    public static final String BASE_URL_PROD = applicationConfig.baseUrlProd();
    public static final String BASE_URL_API = applicationConfig.baseUrlApi();
    public static final String BASE_URL_API_PROD = applicationConfig.baseUrlApiProd();

    public static final boolean BROWSER_HEADLESS = applicationConfig.headless();

    static AuthConfig config = ConfigFactory.create(AuthConfig.class);
    public static final String USER_NAME = config.username();
    public static final String PASSWORD = config.password();
    public static final String APP_PASSWORD = config.appPassword();


    @BeforeAll
    public static void setup() {
        // 1. Настройка Selenide
        Configuration.baseUrl = BASE_URL;
        //Configuration.baseUrl = BASE_URL_PROD;
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        //Configuration.headless = false; // Поставь true для запуска в CI/CD без GUI
        Configuration.headless = BROWSER_HEADLESS; // Поставь true для запуска в CI/CD без GUI

        // 2. Подключаем слушатели Allure для автоматических скриншотов и логов
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));

        // 3. Настройка Rest Assured
        //RestAssured.baseURI = "https://narva-online.ee/wp-json/wp/v2";
        RestAssured.baseURI = BASE_URL_API;
        //RestAssured.baseURI = BASE_URL_API_PROD;
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                // 2. Добавляем настройку игнорирования сертификатов (для OpenServer)
                .setConfig(RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()))
                // Глобально добавляем Basic Auth (preemptive)
                .setAuth(RestAssured.preemptive().basic(USER_NAME, APP_PASSWORD))
                .addFilter(new AllureRestAssured()) // Автоматически добавляет API запросы в отчет Allure
                .build();
    }
}