plugins {
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    java
}
repositories{
    mavenCentral()
}
dependencies{
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.apache.commons:commons-pool2")
    implementation("org.flywaydb:flyway-core")
    implementation("io.netty:netty-buffer:4.1.108.Final")
    implementation("io.netty:netty-handler:4.1.108.Final")
    implementation("io.netty:netty-transport:4.1.108.Final")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    runtimeOnly("org.postgresql:postgresql")

}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}