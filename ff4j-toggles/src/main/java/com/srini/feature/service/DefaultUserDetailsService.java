package com.srini.feature.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.RequiredArgsConstructor;

//@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) {
    // Replaced with UserAuthService
    return new User(username, "srini", Collections.emptyList());
  }
}