package com.srini.feature;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.srini.feature.config.Features;
import com.srini.feature.controller.MessageController;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootTest
class FeatureApplicationTests {

  @MockBean
  private Features features;

  @Autowired
  private MessageController messageController;

  @BeforeAll
  static void setUpClass() {
    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("srini", "srini");
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @BeforeEach
  void setUp() {
    Mockito.reset(features);
  }

  @ParameterizedTest
  @ArgumentsSource(FeatureEnabledOrDisabledArgumentProvider.class)
  // @Disabled
  void shouldReturnMessage(boolean isQcEnabled) {
    // given
    when(features.isQcEnabled()).thenReturn(isQcEnabled);

    // when
    String message = messageController.getMessage();
    System.out.println(message);

    // then
    assertThat(message)
        .isEqualTo(isQcEnabled ? MessageController.QUALITY_CHECK_IS_FINE : MessageController.NON_QUALITY_CHECK);
  }
}