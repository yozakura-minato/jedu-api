package com.jsal.jeduApi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class JeduApiApplication {
    static void main(String[] args) {
        SpringApplication.run(JeduApiApplication.class, args);

        log.info("╔═══════════════════════════════════════════╗");
        log.info("║ JEDU API application started successfully ║");
        log.info("╚═══════════════════════════════════════════╝");
    }
}
