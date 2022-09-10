package com.assignment.shoponline.config;

import com.assignment.shoponline.service.AccountService;
import com.assignment.shoponline.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    final AccountService accountService;
    final PasswordEncoder passwordEncoder;
    private static final String[] IGNORE_PATHS = {
            "/api/v1/accounts/register",
            "/api/v1/accounts/login",
            "/api/v1/products"
    };
    private static final String[] ADMIN_PATHS = {"/api/admin/v1/**"};
    private static final String[] USER_PATHS = {"/api/v1/carts", "/api/v1/orders"};

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        MyAuthenticationFilter authenticationFilter = new MyAuthenticationFilter(authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/api/v1/accounts/login");
        http.cors().and().csrf().disable();
        http.authorizeRequests().antMatchers(null,IGNORE_PATHS).permitAll();
        http.authorizeRequests().antMatchers(USER_PATHS).hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests().antMatchers(ADMIN_PATHS).hasAuthority("ADMIN");
        http.addFilterBefore(new MyAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}