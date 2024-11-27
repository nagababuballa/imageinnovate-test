package com.example.demo.filter;

import com.example.demo.util.Constant;
import com.example.demo.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String jwtToken = extractJwtTokenFromRequest(request);
        String username;
        try {
            username = jwtTokenUtil.extractUsername(jwtToken);
        } catch (Exception e) {
            username = null;
        }
        if (username != null && ! jwtTokenUtil.isTokenExpired(jwtToken)) {
            Claims claims = jwtTokenUtil.extractAllClaims(jwtToken);
            List<String> roles = claims.entrySet().stream()
                    .filter(t->t.getKey().equals(Constant.AUTHORITIES_KEY))
                    .map(t->String.valueOf(t.getValue())).toList();
            List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
            // Create an authentication object
            JwtAuthenticationToken authentication = new JwtAuthenticationToken(username, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    // Extract JWT token from Authorization header
    private String extractJwtTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}
