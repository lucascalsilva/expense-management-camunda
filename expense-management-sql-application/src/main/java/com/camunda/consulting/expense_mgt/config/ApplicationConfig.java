package com.camunda.consulting.expense_mgt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@EnableScheduling
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final ProcessEngineServicesConfig processEngineServicesConfig;

    @Bean
    public RestTemplate camundaRestTemplate(ClientHttpRequestInterceptor clientHttpRequestInterceptor){
        return new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(processEngineServicesConfig.getTimeout()))
                .setReadTimeout(Duration.ofMillis(processEngineServicesConfig.getTimeout()))
                .additionalInterceptors(clientHttpRequestInterceptor)
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .build();
    }
}
