package com.theodo.albeniz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodo.albeniz.client.LastFmApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@Slf4j
public class LastFmClientConfig {

    private final ObjectMapper objectMapper;

    @Autowired
    private final ApplicationConfig applicationConfig;

    @Bean
    public LastFmApiClient getLastFmClient() {
        return getEsgInvestApiClient();
    }

    private LastFmApiClient getEsgInvestApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(applicationConfig.getLastFmApi().getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(new OkHttpClient())
                .build();

        return retrofit.create(LastFmApiClient.class);
    }
}
