package com.theodo.albeniz.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
@EnableMethodSecurity
@Getter
@Setter
public class ApplicationConfig {

    private ApiConfiguration api = new ApiConfiguration();
    private LastFmApiConfiguration lastFmApi = new LastFmApiConfiguration();

    @Getter
    @Setter
    public static class ApiConfiguration {
        int maxCollection = 30;
        boolean ascending = true;
    }

    @Getter
    @Setter
    public static class LastFmApiConfiguration {
        String baseUrl = "baseUrl";
    }
}
