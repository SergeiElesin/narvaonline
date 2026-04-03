package com.elesinsergei.narvaonline.config;

import org.aeonbits.owner.Config;
/**
 * Reading auth.properties file
 * Getting properties
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",                     // 1. First - searching in environment variables
        "system:properties",              // // 2. Second - searching in subsystem properties (-Dkey=value)
        "classpath:auth.properties" // // 3. At last - searching in file auth.properties
})
public interface AuthConfig extends Config {

    @Key("USER_NAME")
    String username();

    @Key("PASSWORD")
    String password();

    @Key("APP_PASSWORD")
    String appPassword();
}
