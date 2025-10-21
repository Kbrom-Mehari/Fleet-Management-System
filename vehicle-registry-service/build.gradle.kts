plugins {
    // spring boot plugin
    id("org.springframework.boot") version "3.5.6"
    //spring dependency management plugin
    id("io.spring.dependency-management") version "1.1.7"
    java
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    compileOnly("org.projectlombok:lombok")
    implementation("com.google.guava:guava:32.1.2-jre")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
