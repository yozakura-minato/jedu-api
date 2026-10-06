plugins {
    java
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.jsal"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(27)
    }
}

repositories {
    mavenCentral()
}

val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    // WEB & DOCUMENTATION ---------------------------------------------------------------------------------------------
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:3.1.1")

    // SECURITY --------------------------------------------------------------------------------------------------------
//    implementation("org.springframework.boot:spring-boot-starter-security")
//    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
//
//    implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
//    testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server-test")

    // PERSISTENCE -----------------------------------------------------------------------------------------------------
    runtimeOnly("org.postgresql:postgresql")
//    implementation("org.springframework.boot:spring-boot-starter-data-redis")
//    testImplementation("org.springframework.boot:spring-boot-starter-data-redis-test")

    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    testImplementation("org.springframework.boot:spring-boot-starter-jdbc-test")

    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.flywaydb:flyway-database-postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")

    implementation("com.github.gavlyukovskiy:p6spy-spring-boot-starter:2.0.1")

    // DEVELOPMENT TOOLS -----------------------------------------------------------------------------------------------
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")

    // DEVELOPMENT UTILITIES -------------------------------------------------------------------------------------------
    implementation("org.springframework.boot:spring-boot-starter-validation")
    testImplementation("org.springframework.boot:spring-boot-starter-validation-test")

    compileOnly("org.projectlombok:lombok:1.18.48")
    annotationProcessor("org.projectlombok:lombok:1.18.48")
    testCompileOnly("org.projectlombok:lombok:1.18.48")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.48")

    mockitoAgent("org.mockito:mockito-core") {
        isTransitive = false
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(
        "-javaagent:${mockitoAgent.asPath}",
        "-Xshare:off",
        "-Duser.timezone=UTC"
    )
}
