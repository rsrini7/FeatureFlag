package com.srini.feature.newrelic.client;

import com.srini.feature.newrelic.dto.Deployment;

public interface NewRelicClient {
  void createDeployment(Deployment deployment);
}