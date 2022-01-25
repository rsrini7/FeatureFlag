package com.srini.feature.config;

import org.ff4j.FF4j;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Features {
  private static final String QC = "fflag-qc";

  private final FF4j ff4j;

  public boolean isQcEnabled() {
    return ff4j.check(QC);
  }
}