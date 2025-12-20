plugins {
    // spring boot plugin
    alias(libs.plugins.spring.boot) //includes the spring boot BOM
    alias(libs.plugins.spring.dependency.management)
    java
}

dependencies {
    implementation(libs.spring.web)
    implementation(libs.spring.data.jpa)
    implementation(libs.spring.data.jdbc)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
    implementation(libs.guava)
    testImplementation(libs.spring.test)
    runtimeOnly(libs.postgresql)

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
