plugins {
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    java
}
repositories{
    mavenCentral()
}
dependencies{
    implementation(libs.spring.web)
    implementation(libs.spring.data.jpa)
    implementation(libs.spring.redis)
    implementation("org.apache.commons:commons-pool2")
    implementation(libs.flyway)
    implementation("io.netty:netty-buffer:4.1.118.Final")
    implementation("io.netty:netty-handler:4.1.118.Final")
    implementation("io.netty:netty-transport:4.1.118.Final")
    implementation(libs.spring.kafaka)
    testImplementation(libs.spring.test)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    runtimeOnly(libs.postgresql)

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