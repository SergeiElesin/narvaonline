package com.elesinsergei.narvaonline.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdvertsUiTestData {

    //Test endpoints
    public static final String ADS_PAGE  = "/obyavleniya/";
    public static final String ADD_AD_PAGE = "/obyavleniya/dobavit-obyavleniye/";
    public static final String MY_ADS_PAGE = "/obyavleniya/izmenit-obyavleniye/";
    public static final String MY_ACCOUNT = "/account/";

    // User test data
    public static final String TIMESTAMP = LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyMMdd_HHmmss"));
    public static final String TEST_USERNAME = "test_user_" + TIMESTAMP;
    public static final String TEST_EMAIL    = "test_" + TIMESTAMP + "@example.com";
    public static final String TEST_PASSWORD = "TestPass123!";

    // Ads data
    public static final String AD_TITLE = "Тестовое объявление " + TIMESTAMP;
    public static final String AD_DESCRIPTION = "Описание тестового объявления. Создано автоматически.";
    public static final String AD_PHONE = "33337777";
    public static final String AD_PRICE = "999";
    public static final String AD_LOCATION = "Paradise City";
}
