package com.srini.feature.newrelic.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.srini.feature.newrelic.dto.Deployment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class HttpNewRelicClient implements NewRelicClient {
  @Qualifier("newRelicRestTemplate")
  private final RestTemplate restTemplate;
  @Value("${newrelic.deployments.url}")
  private String deploymentUrl;
  @Value("${newrelic.deployments.api-key}")
  private String newRelicApiKey;

  private ObjectMapper objectMapper;

  @PostConstruct
  public void init() {
    objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
  }

  @Override
  public void createDeployment(Deployment deployment) {
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");
    httpHeaders.set("X-Api-Key", newRelicApiKey);
    String deploymentJson;
    try {
      deploymentJson = objectMapper.writeValueAsString(deployment);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    HttpEntity<String> entity = new HttpEntity<>(deploymentJson, httpHeaders);
    restTemplate.exchange(deploymentUrl, HttpMethod.POST, entity, String.class);
  }
}