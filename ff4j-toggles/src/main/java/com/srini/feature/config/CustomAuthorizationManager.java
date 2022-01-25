package com.srini.feature.config;

import org.ff4j.security.AbstractAuthorizationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Set;

import lombok.RequiredArgsConstructor;

import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager extends AbstractAuthorizationManager {
  private final UserDetailsService userDetailsService;

  @Override
  public Set<String> getCurrentUserPermissions() {
    String userName = getCurrentUserName();
    return userDetailsService.loadUserByUsername(userName).getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(toSet());
  }

  @Override
  public Set<String> listAllPermissions() {
    return getCurrentUserPermissions();
  }

  @Override
  public String getCurrentUserName() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }
}