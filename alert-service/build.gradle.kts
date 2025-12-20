plugins {
    //spring boot plugin automatically imports the BOM like spring-boot-dependencies:x.x.x
    alias(libs.plugins.spring.dependency.management)
    alias(libs.plugins.spring.boot)
    java
}
dependencies {

    implementation(libs.spring.web)
    runtimeOnly(libs.postgresql)
    testImplementation(libs.spring.test)
}
java{
toolchain{
    languageVersion=JavaLanguageVersion.of(21)
}
}
tasks.withType<Test> {
    useJUnitPlatform()
}