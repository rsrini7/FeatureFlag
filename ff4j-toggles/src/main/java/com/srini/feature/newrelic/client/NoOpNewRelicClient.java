package com.srini.feature.newrelic.client;

import com.srini.feature.newrelic.dto.Deployment;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
public class NoOpNewRelicClient implements NewRelicClient {
  @Override
  public void createDeployment(Deployment deployment) {

  }
}
