plugins {
    java
    //Allure plugin
    id("io.qameta.allure") version "3.0.2"
}

group = "com.elesinsergei"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val selenideVersion = "7.14.0"
val restAssuredVersion = "5.5.6"
val allureVersion = "2.31.0"
val junitVersion = "5.10.2"
val slf4jVersion = "2.0.12"
val aspectjVersion = "1.9.22"

dependencies {

    // Main Owner library
    implementation("org.aeonbits.owner:owner:1.0.12")

    // Lombok
    implementation("org.projectlombok:lombok:1.18.42")
    annotationProcessor("org.projectlombok:lombok:1.18.42")

    testImplementation("org.projectlombok:lombok:1.18.42")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.42")

    // Import allure-bom to ensure correct versions of all the dependencies are used
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    // Add necessary Allure dependencies to dependencies section
    testImplementation("io.qameta.allure:allure-junit5")

    // UI
    implementation("com.codeborne:selenide:${selenideVersion}")
    //testImplementation("com.codeborne:selenide:${selenideVersion}")

    // API
    implementation("io.rest-assured:rest-assured:${restAssuredVersion}")
    implementation("io.rest-assured:json-schema-validator:${restAssuredVersion}")

    // JUnit 5
    testImplementation("org.junit.jupiter:junit-jupiter:${junitVersion}")

    //  AssertJ
    testImplementation("org.assertj:assertj-core:3.27.7")
    implementation("org.assertj:assertj-core:3.27.7")

    // Log
    testImplementation("org.slf4j:slf4j-simple:${slf4jVersion}")

    // Allure tools integration
    testImplementation("io.qameta.allure:allure-selenide:${allureVersion}")
    implementation("io.qameta.allure:allure-selenide:${allureVersion}")
    testImplementation("io.qameta.allure:allure-rest-assured:${allureVersion}")
    implementation("io.qameta.allure:allure-rest-assured:${allureVersion}")
    testImplementation("io.qameta.allure:allure-junit5:${allureVersion}")
    implementation("io.qameta.allure:allure-assertj:${allureVersion}")

    //Class watcher (recommended testRuntimeOnly)
    testRuntimeOnly("org.aspectj:aspectjweaver:${aspectjVersion}")

    // JSON (serialization/deserialization POJO)
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}


allure {
    version.set(allureVersion)
    adapter {
        aspectjVersion.set("1.9.22") // Needed for @Step annotation
        frameworks {
            junit5 {
                enabled.set(true)
                adapterVersion.set("2.31.0")
            }
        }
    }
}
/*

tasks.test {
    useJUnitPlatform()
    // Passing system properties (for example, to select a browser or URL)
    systemProperties(System.getProperties().asIterable().associate { it.key.toString() to it.value })
    testLogging {
        events("passed", "skipped", "failed")
    }
}
*/

tasks.test {
    useJUnitPlatform {
        //for tag using in testing tasks (./gradlew test -Dgroups=api)
        val groups = System.getProperty("groups")
        if (groups != null) {
            includeTags(groups)
        }
    }
    //Passing system properties (for example, to select a browser or URL)
    systemProperties(System.getProperties().asIterable().associate { it.key.toString() to it.value })
    testLogging {
        events("passed", "skipped", "failed")
    }
}

//project will be built on the same version of Java for all developers and on the server (CI/CD)
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

