package com.assignment.shoponline.config;

import com.assignment.shoponline.entity.dto.AccountLoginDto;
import com.assignment.shoponline.entity.dto.CredentialDto;
import com.assignment.shoponline.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import static sun.security.timestamp.TSResponse.BAD_REQUEST;

@Slf4j
public class MyAuthenticationFilter extends UsernamePasswordAuthenticationFilter { //dung de xac thuc va cap token
    final AuthenticationManager authenticationManager;

    public MyAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            log.info("Attempt Authentication");
//            String username = request.getParameter("username");
//            String password = request.getParameter("password");
            AccountLoginDto accountLoginDto = new ObjectMapper()
                    .readValue(request.getInputStream(), AccountLoginDto.class);
            String username = accountLoginDto.getUsername();
            String password = accountLoginDto.getPassword();
            log.info("User with username {} and password {} is logging", username, password);
            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(userToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("Start generate token");
        User user = (User) authResult.getPrincipal();
        //cấp access token và refresh token cho user vừa login
        String accessToken = JwtUtil.generateToken(user.getUsername(),
                user.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURL().toString());
        String refreshToken = JwtUtil.generateToken(user.getUsername(),
                user.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURL().toString());
        String scope = user.getAuthorities().iterator().next().getAuthority();
        CredentialDto credentialDto = new CredentialDto(accessToken, refreshToken, JwtUtil.EXPIRED_TIME, scope);
        //setup response ve Authorization
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Gson gson = new Gson();
        response.getWriter().println(gson.toJson(credentialDto)); //send credential ve client
        log.info("Successfully Authentication for user {}", user.getUsername());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("message", "Invalid information");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Gson gson = new Gson();
        response.getWriter().println(gson.toJson(errors));
        response.setStatus(400);
    }
}
