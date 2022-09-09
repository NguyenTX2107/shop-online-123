package com.assignment.shoponline.config;

import com.assignment.shoponline.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] IGNORE_PATHS = {
            "/", "/css/*", "/img/*", "/favicon.ico",
            "/v1/api-docs",
            "/api/v1/products/**",
            "/api/v1/accounts/**"
    };

    private static final String[] ADMIN_PATHS = {"/api/admin/v1/**"};
    private static final String[] USER_PATHS = {"/api/v1/**"};

    @Bean

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        http.cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
        http.authorizeRequests()
                .antMatchers(IGNORE_PATHS).permitAll()
                .antMatchers(USER_PATHS).hasAnyAuthority(Enums.Role.USER.name(), Enums.Role.ADMIN.name())
                .antMatchers(ADMIN_PATHS).hasAuthority(Enums.Role.ADMIN.name());
        http.addFilterBefore(
                new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}