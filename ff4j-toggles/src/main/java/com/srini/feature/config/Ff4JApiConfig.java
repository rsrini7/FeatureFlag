package com.srini.feature.config;

import org.ff4j.FF4j;
import org.ff4j.web.ApiConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class Ff4JApiConfig {
  @Value("${server.port:8080}")
  private int port;

  @Bean
  public ApiConfig getApiConfig(FF4j ff4j) {
    ApiConfig apiConfig = new ApiConfig();
    apiConfig.setPort(port);
    apiConfig.setFF4j(ff4j);
    apiConfig.setDocumentation(true);
    return apiConfig;
  }

}