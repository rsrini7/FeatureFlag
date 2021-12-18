package com.example.demo;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.split.client.SplitClient;
import io.split.client.SplitClientConfig;
import io.split.client.SplitFactory;
import io.split.client.SplitFactoryBuilder;
import io.split.client.impressions.Impression;
import io.split.client.impressions.ImpressionListener;
import io.split.integrations.IntegrationsConfig;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // config.setAllowCredentials(true);
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

    @Configuration
    public class WebConfiguration implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**").allowedMethods("*");
        }
    }

    @Configuration
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("demo").password("{noop}demo").roles("USER").and()
                    .withUser("demo2").password("{noop}demo").roles("USER").and()
                    .withUser("user1").password("{noop}demo").roles("USER").and()
                    .withUser("srini").password("{noop}srini").roles("USER");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .httpBasic()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/demo/**").authenticated()
                    .antMatchers("/**").permitAll()
                    // .anyRequest().authenticated()
                    .and().csrf().disable();
        }

    }

    @Configuration
    public class SplitConfig {

        @Value("#{ @environment['split.api.key'] }")
        private String apiKey;

        @Bean
        public SplitClient splitClient() throws Exception {
            SplitClientConfig config = SplitClientConfig.builder()
                    .integrations(
                            IntegrationsConfig.builder()
                                    .impressionsListener(new MyImpressionListener(), 50)
                                    .build())
                    .setBlockUntilReadyTimeout(20000)
                    .enableDebug()
                    .build();

            SplitFactory splitFactory = SplitFactoryBuilder.build(apiKey, config);
            SplitClient client = splitFactory.client();
            client.blockUntilReady();

            return client;
        }

        // @Bean
        public SplitClient splitClientLocalhost() throws Exception {
            String file = Paths.get("split.yaml").toFile().getAbsolutePath();
            SplitClientConfig config = SplitClientConfig.builder()
                    .splitFile(file)
                    .enableDebug()
                    .build();

            SplitFactory splitFactory = SplitFactoryBuilder.build("localhost", config);
            SplitClient client = splitFactory.client();
            client.blockUntilReady();

            return client;
        }
    }

    static class MyImpressionListener implements ImpressionListener {
        private Logger logger = LoggerFactory.getLogger(MyImpressionListener.class);

        @Override
        public void log(Impression impression) {
            // Send this data somewhere. Printing to console for now.
            logger.debug(impression.toString());
        }

        @Override
        public void close() {
            logger.debug("impression cloed");
        }
    }

}
