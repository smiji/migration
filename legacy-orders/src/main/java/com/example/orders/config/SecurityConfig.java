package com.example.orders.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/** Deprecated in Spring Security 5.7 (Boot 2.7), removed in Security 6 (Boot 3). */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/actuator/health", "/swagger-ui/**", "/v3/api-docs/**",
                             "/swagger-resources/**", "/webjars/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .httpBasic();
    }
}
