plugins {
    alias(libs.plugins.spring.dependency.management) //spring dependency management
    alias(libs.plugins.spring.boot) //spring boot plugin
    java
}
dependencies{
    //dependency aliases - cleaner and less prone to typos
    implementation(libs.spring.web)
    implementation(libs.spring.data.jpa)
    implementation(libs.spring.redis)
    implementation(libs.apache.commons.pool2)
    implementation(libs.flyway)
    implementation(libs.netty.all)
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