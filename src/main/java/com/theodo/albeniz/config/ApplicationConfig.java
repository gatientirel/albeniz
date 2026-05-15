package com.theodo.albeniz.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Configuration()
@ConfigurationProperties(prefix = "application")
@Component()
public class ApplicationConfig {

    @Getter()
    @Setter()
    private ApiConfiguration api;

    @Configuration()
    @ConfigurationProperties(prefix = "api")
    public static class ApiConfiguration {
        @Getter()
        @Setter()
        private int maxCollection;
        @Getter()
        @Setter()
        private boolean ascending;

    }

}
