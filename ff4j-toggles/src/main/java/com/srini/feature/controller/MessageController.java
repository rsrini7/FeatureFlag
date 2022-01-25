package com.srini.feature.controller;

import com.srini.feature.config.Features;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MessageController {
  private final Features features;

  @GetMapping("/message")
  public String getMessage() {
    return features.isQcEnabled() ? "QC" : "NON-QC";
  }
}