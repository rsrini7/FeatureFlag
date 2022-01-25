package com.srini.feature.newrelic.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;

@Data
@JsonRootName("deployment")
public class Deployment {
  private String revision;
  private String changelog;
  private String user;
}