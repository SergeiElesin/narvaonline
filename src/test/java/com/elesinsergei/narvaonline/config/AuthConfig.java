package com.elesinsergei.narvaonline.config;

import org.aeonbits.owner.Config;
/**
 * Читаем файл auth.properties
 * Достаем из него проперти
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:env",                     // 1. Сначала ищем в переменных окружения
        "system:properties",              // 2. Потом в системных пропертях (-Dkey=value)
        "classpath:auth.properties" // 3. И только потом в файле
})
public interface AuthConfig extends Config {

    @Key("USER_NAME")
    String username();

    @Key("PASSWORD")
    String password();

    @Key("APP_PASSWORD")
    String appPassword();
}
