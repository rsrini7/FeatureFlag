package com.srini.feature.config;

import com.srini.feature.strategy.UserFlippingStrategy;

import org.ff4j.FF4j;
import org.ff4j.core.FlippingExecutionContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Features {
  private static final String QC = "fflag-quality-check-1";

  private final FF4j ff4j;

  public boolean isQcEnabled() {
    return ff4j.check(QC);
  }

  public boolean isQcEnabled(String id) {
    FlippingExecutionContext flippingExecutionContext = new FlippingExecutionContext();
    flippingExecutionContext.addValue(UserFlippingStrategy.PARAMNAME_USER, id);
    // System.out.println("\t\t--->>>>>>" +
    // ff4j.getFeature(QC).getProperty("users"));
    return ff4j.check(QC, flippingExecutionContext);
  }
}