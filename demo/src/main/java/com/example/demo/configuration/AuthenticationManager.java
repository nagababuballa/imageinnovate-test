/*
package com.example.demo.configuration;

import com.example.demo.util.Constant;
import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        String username;
        Logger log = LogManager.getLogger(AuthenticationManager.class);
        try {
            username = jwtUtil.extractUsername(authToken);
        } catch (Exception e) {
            log.error("exception is---",e);
            log.info("username is null");
            username = null;
        }
        if (username != null && ! jwtUtil.isTokenExpired(authToken)) {
            System.out.println("authToken--"+authToken);
            Claims claims = jwtUtil.extractAllClaims(authToken);
            List<String> roles = claims.entrySet().stream()
                    .filter(t->t.getKey().equals(Constant.AUTHORITIES_KEY))
                    .map(t->String.valueOf(t.getValue())).toList();
            List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, username, authorities);
            SecurityContextHolder.getContext().setAuthentication(new AuthenticatedUser(username, authorities));
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}*/
