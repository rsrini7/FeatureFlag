package com.srini.feature;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class FeatureEnabledOrDisabledArgumentProvider implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(
        Arguments.of(true),
        Arguments.of(false));
  }
}