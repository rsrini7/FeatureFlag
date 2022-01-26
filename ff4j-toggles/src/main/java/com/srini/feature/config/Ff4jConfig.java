package com.srini.feature.config;

import javax.sql.DataSource;

import org.ff4j.FF4j;
import org.ff4j.audit.repository.EventRepository;
import org.ff4j.audit.repository.JdbcEventRepository;
import org.ff4j.cache.FF4JCacheManager;
import org.ff4j.cache.FF4jCacheManagerRedis;
import org.ff4j.core.FeatureStore;
import org.ff4j.property.store.JdbcPropertyStore;
import org.ff4j.property.store.PropertyStore;
import org.ff4j.redis.RedisConnection;
import org.ff4j.security.AuthorizationsManager;
import org.ff4j.spring.boot.web.api.config.EnableFF4jSwagger;
import org.ff4j.store.JdbcFeatureStore;
import org.ff4j.web.FF4jDispatcherServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableFF4jSwagger
@EnableSwagger2
@ConditionalOnClass({ FF4jDispatcherServlet.class })
@RequiredArgsConstructor
public class Ff4jConfig extends SpringBootServletInitializer {

  @Value("${redis.host}")
  private String redisHost;

  private final Environment environment;
  private final DataSource dataSource;
  // private final NewRelicClient newRelicDeploymentsClient;
  // private SpringSecurityAuthorisationManager ff4jSecMgr;

  @Bean
  public JdbcTemplate getJdbcTemplate() throws ClassNotFoundException {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(environment.getRequiredProperty("spring.datasource.driver-class-name"));
    dataSource.setUrl(environment.getRequiredProperty("spring.datasource.url"));
    dataSource.setUsername(environment.getRequiredProperty("spring.datasource.username"));
    dataSource.setPassword(environment.getRequiredProperty("spring.datasource.password"));
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    return jdbcTemplate;
  }

  @Bean
  public FF4j getFF4j(AuthorizationsManager authorizationsManager) {
    FF4j ff4j = new FF4j();
    ff4j.setFeatureStore(featureStore());
    ff4j.setEventRepository(eventRepository());
    ff4j.setPropertiesStore(propertyStore());
    ff4j.audit(true);
    ff4j.cache(ff4jCacheManager());
    ff4j.setAuthorizationsManager(authorizationsManager);
    // ff4j.setAuthorizationsManager(ff4jSecMgr);
    return ff4j;
  }

  @Bean
  public ServletRegistrationBean<FF4jDispatcherServlet> ff4jDispatcherServletRegistrationBean(
      FF4jDispatcherServlet ff4jDispatcherServlet) {
    ServletRegistrationBean<FF4jDispatcherServlet> bean = new ServletRegistrationBean<FF4jDispatcherServlet>(
        ff4jDispatcherServlet, "/ff4j-console/*");
    bean.setName("ff4j-console");
    bean.setLoadOnStartup(1);
    return bean;
  }

  @Bean
  @ConditionalOnMissingBean
  public FF4jDispatcherServlet getFF4jDispatcherServlet(AuthorizationsManager authorizationsManager) {
    FF4jDispatcherServlet ff4jConsoleServlet = new FF4jDispatcherServlet();
    ff4jConsoleServlet.setFf4j(getFF4j(authorizationsManager));
    return ff4jConsoleServlet;
  }

  @Bean
  public FF4JCacheManager ff4jCacheManager() {
    return new FF4jCacheManagerRedis(new RedisConnection(redisHost, 6379));
  }

  @Bean
  public FeatureStore featureStore() {
    return new JdbcFeatureStore(dataSource);
  }

  @Bean
  public EventRepository eventRepository() {
    // return new CustomEventRepository(new JdbcEventRepository(dataSource),
    // newRelicDeploymentsClient);
    return new JdbcEventRepository(dataSource);
  }

  @Bean
  public PropertyStore propertyStore() {
    return new JdbcPropertyStore(dataSource);
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.addExposedHeader("Content-Range");
    config.addExposedHeader("Content-Type");
    config.addExposedHeader("Accept");
    config.addExposedHeader("X-Requested-With");
    config.addExposedHeader("remember-me");
    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  // @Bean
  // public SpringSecurityAuthorisationManager ff4SecuirtyAuthManager() {
  // return new SpringSecurityAuthorisationManager();
  // }

}