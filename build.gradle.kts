plugins {
    id("java")
    id("application")
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
}


allprojects {
    group = "cheboksarov"
    version = "0.0.1-SNAPSHOT"
    // Применяем плагины для всех проектов
    apply(plugin = "java")
    apply(plugin = "application")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17)) // Указываем версию Java
        }
    }
    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }
    dependencies{
        implementation("org.springframework.boot:spring-boot-starter-quartz")
        implementation("org.springframework.kafka:spring-kafka")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("org.springframework.boot:spring-boot-starter-security:3.2.4")
        implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server:3.2.4")
        runtimeOnly("org.postgresql:postgresql:42.2.27")
        // https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-jta-atomikos
        implementation("org.springframework.boot:spring-boot-starter-jta-atomikos:2.7.18")
        // https://mvnrepository.com/artifact/javax.transaction/jta
        implementation("javax.transaction:jta:1.1")
        // https://mvnrepository.com/artifact/com.nimbusds/nimbus-jose-jwt
        implementation("com.nimbusds:nimbus-jose-jwt:9.40")
        // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-api
        implementation("io.jsonwebtoken:jjwt-api:0.12.5")
        // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-impl
        runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
        // https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt-jackson
        runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")

        // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")


        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}