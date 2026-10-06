> # JEDU API
>
> JSAL Education System API Application

## 🛠️ Tech Stack

![My Skills](https://skillicons.dev/icons?i=[NULL],spring,java,postgres,redis)

* `Spring Boot` in `Java`
* `PostgreSQL` for database
* `Redis` for caching

## 🧰 Dev Tools

![My Skills](https://skillicons.dev/icons?i=[NULL],idea,gradle,docker)

* `IntelliJ IDEA` for IDE
* `Gradle Kotlin` for dependency management
* `Docker` for container management

## ⬇️ Installation

* [IntelliJ IDEA](https://code.visualstudio.com/download)
* [Docker Desktop](https://docs.docker.com/desktop/setup/install/windows-install/)

## 🚀 Getting Started

1. Clone this project
2. Open with `IntelliJ IDEA`
3. Download dependencies with `Gradle Kotlin` in [build.gradle.kts](`build.gradle.kts`)
4. In `Run/Debug Configurations` dialog, set `Add VM Options` as

    ```
    -Duser.timezone=Asia/Ho_Chi_Minh
    ```

   and `Active Profiles` as

    ```
    dev
    ```

5. Create **compose.yaml** (see [`compose.example.yaml`](compose.example.yaml))
6. Create **application-dev.yaml** (see [`application-dev.example.yaml`](src/main/resources/application-dev.example.yaml))
7. Start `Docker Deamon`
8. Create and start containers

    ```shell
    docker compose up
    ```

9. Run the local API application

   _The local API application is now running at: http://localhost:8080_

    ```
    ╔═══════════════════════════════════════════╗
    ║ JEDU API application started successfully ║
    ╚═══════════════════════════════════════════╝
    ```

   _See API documentation at: http://localhost:8080/swagger-ui/index.html#/_

## 🗂️ Appendix

### Dependencies

* Web & API documentation
    * `Spring Web` for web core
    * `SpringDoc OpenAPI` for API documentation

* Security
    * `Spring Security` for security
    * `OAuth2 Resource Server` for authentication & authorization

* Persistence

    * `PostgreSQL Driver` for PostgreSQL driver
    * `Spring Data Redis (Access+Driver)` Redis driver
    * `JDBC API` for database connection
    * `Flyway Migration` for database migration
    * `P6Spy` for for SQL logging

* Development tools

    * `JUnit` for testing
    * `Spring Boot Actuator` for monitoring

* Development utilities

    * `Validation` for validation utils
    * `Lombok` for code generation

### Documentation

* [Spring Web](https://docs.spring.io/spring-boot/4.1.1/reference/web/servlet.html)
* [SpringDoc OpenAPI](https://springdoc.org/)
* [Spring Security](https://docs.spring.io/spring-boot/4.1.1/reference/web/spring-security.html)
* [OAuth2 Resource Server](https://docs.spring.io/spring-boot/4.1.1/reference/web/spring-security.html#web.security.oauth2.server)
* [Spring Data Redis (Access+Driver)](https://docs.spring.io/spring-boot/4.1.1/reference/data/nosql.html#data.nosql.redis)
* [JDBC API](https://docs.spring.io/spring-boot/4.1.1/reference/data/sql.html)
* [Flyway Migration](https://docs.spring.io/spring-boot/4.1.1/how-to/data-initialization.html#howto.data-initialization.migration-tool.flyway)
* [P6Spy](https://github.com/p6spy/p6spy)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/4.1.1/reference/actuator/index.html)
* [Validation](https://docs.spring.io/spring-boot/4.1.1/reference/io/validation.html)

### Guides

* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [SpringDoc OpenAPI](https://github.com/springdoc/springdoc-openapi-demos/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)
* [Accessing Relational Data using JDBC with Spring](https://spring.io/guides/gs/relational-data-access/)
* [Managing Transactions](https://spring.io/guides/gs/managing-transactions/)
* [Using P6Spy with Spring Boot Data Source Decorator](https://github.com/gavlyukovskiy/spring-boot-data-source-decorator)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Additional Links

* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)
* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/4.1.1/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/4.1.1/gradle-plugin/packaging-oci-image.html)
* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
