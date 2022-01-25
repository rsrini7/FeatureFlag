package com.srini.feature.newrelic.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class NewRelicConfig {
  @Bean(name = "newRelicRestTemplate")
  public RestTemplate newRelicRestTemplate() {
    return new RestTemplateBuilder()
        .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
        .setReadTimeout(Duration.ofSeconds(30))
        .setConnectTimeout(Duration.ofSeconds(30))
        .build();
  }
}