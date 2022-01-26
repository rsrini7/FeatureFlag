package com.srini.feature.controller;

import com.srini.feature.config.Features;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {
  public static final String NON_QUALITY_CHECK = "NON Quality Check";
  public static final String QUALITY_CHECK_IS_FINE = "Quality Check is fine";
  private final Features features;

  @GetMapping("/message")
  public String getMessage() {
    return features.isQcEnabled() ? QUALITY_CHECK_IS_FINE : NON_QUALITY_CHECK;
  }

  @GetMapping("/message/{id}")
  public String getMessage(@PathVariable String id) {
    return features.isQcEnabled(id) ? QUALITY_CHECK_IS_FINE : NON_QUALITY_CHECK;
  }
}