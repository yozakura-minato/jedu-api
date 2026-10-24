package com.jsal.jeduApi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JeduApiApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(JeduApiApplication.class);

    static void main(String[] args) {
        SpringApplication.run(JeduApiApplication.class, args);

        LOGGER.info("╔═══════════════════════════════════════════╗");
        LOGGER.info("║ JEDU API application started successfully ║");
        LOGGER.info("╚═══════════════════════════════════════════╝");
    }
}
