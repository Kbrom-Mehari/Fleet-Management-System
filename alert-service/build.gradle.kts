plugins {
    //spring boot plugin automatically imports the BOM like spring-boot-dependencies:x.x.x
    alias(libs.plugins.spring.boot)
    java
}
repositories {
    mavenCentral()
}
dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
java{
toolchain{
    languageVersion=JavaLanguageVersion.of(21)
}
}
tasks.withType<Test> {
    useJUnitPlatform()
}