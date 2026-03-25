package com.elesinsergei.narvaonline.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",                     // 1. Сначала ищем в переменных окружения
        "system:properties",              // 2. Потом в системных пропертях (-Dkey=value)
        "classpath:application.properties" // 3. И только потом в файле
})
public interface ApplicationConfig extends Config {

    @Key("BASE_URL")
    String baseUrl();

    @Key("BASE_URL_PROD")
    String baseUrlProd();

    @Key("BASE_URL_API")
    String baseUrlApi();

    @Key("BASE_URL_API_PROD")
    String baseUrlApiProd();

    @Key("BROWSER_HEADLESS")
    Boolean headless();
}
