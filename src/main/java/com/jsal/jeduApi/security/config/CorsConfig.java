package com.jsal.jeduApi.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * @author Yozakura Minato
 */
@Configuration
class CorsConfig {
    @Value(value = "${jedu.api.security.allowed-methods}")
    private List<String> apiAllowedMethods;

    @Value(value = "${jedu.api.security.allowed-headers}")
    private List<String> apiAllowedHeaders;

    @Value(value = "${jedu.api.security.cors-url-pattern}")
    private String apiCorsPattern;

    @Value(value = "${jedu.web.base-url}")
    private String webBaseUrl;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of(webBaseUrl));
        config.setAllowedMethods(apiAllowedMethods);
        config.setAllowedHeaders(apiAllowedHeaders);
        config.setAllowCredentials(true);
        config.setMaxAge(3_600L);

        UrlBasedCorsConfigurationSource urlConfigSource = new UrlBasedCorsConfigurationSource();
        urlConfigSource.registerCorsConfiguration(apiCorsPattern, config);
        return urlConfigSource;
    }
}
