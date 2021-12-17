package com.example.demo;

import io.split.client.SplitClient;
import io.split.client.SplitClientConfig;
import io.split.client.SplitFactory;
import io.split.client.SplitFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
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
                    .anyRequest().authenticated()
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
                    .setBlockUntilReadyTimeout(20000)
                    .enableDebug()
                    .build();

            SplitFactory splitFactory = SplitFactoryBuilder.build(apiKey, config);
            SplitClient client = splitFactory.client();
            client.blockUntilReady();

            return client;
        }
    }

}
