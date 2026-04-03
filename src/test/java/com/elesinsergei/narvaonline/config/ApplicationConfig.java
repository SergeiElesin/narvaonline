package com.elesinsergei.narvaonline.config;

import org.aeonbits.owner.Config;

/**
 * Reading application.properties file
 * Getting properties
 */

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",                     // 1. First - searching in environment variables
        "system:properties",              // 2. Second - searching in subsystem properties (-Dkey=value)
        "classpath:application.properties" // 3. At last - searching in file application.properties
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
