package com.example.ParlourApp.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomerUserDetailsService service;

    Claims claims = null;
    private String userName = null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String requestPath = httpServletRequest.getServletPath();

        // Public endpoints that do not require authentication
        if (requestPath.equals("/admin/AdminLogin") ||
                requestPath.equals("/parlour/ParlourLogin") ||
                requestPath.equals("/user/UserLogin") ||
                requestPath.equals("/admin/AdminReg") ||
                requestPath.equals("/user/UserReg") ||
                requestPath.equals("/parlour/ParlourReg")) {

            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            userName = jwtUtil.extractUsername(token);
            claims = jwtUtil.extractAllClaims(token);
            log.info("Token: {}, Username extracted: {}", token, userName);

        }
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = service.loadUserByUsername(userName);
            if (jwtUtil.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                String requestedEndpoint = httpServletRequest.getServletPath();
                if (!hasRequiredAuthority(userDetails, requestPath)) {
                    log.warn("User {} does not have the required authority to access {}", userName, requestPath);
                    httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            } else {
                log.error("Invalid token for user:{}", userName);
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
    private boolean hasRequiredAuthority(UserDetails userDetails, String requestPath) {
        String requiredRole = getRequiredRole(requestPath);
        if (requiredRole != null) {
            return userDetails.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_" + requiredRole));
        }
        return true; // if no required role is specified, allow access
    }
    private String getRequiredRole(String requestPath) {
        if (requestPath.startsWith("/admin/")) {
            return "ADMIN";
        } else if (requestPath.startsWith("/parlour/")) {
            return "PARLOUR";
        } else if (requestPath.startsWith("/user/")) {
            return "USER";
        }
        return null; // if no required role is specified, return null
    }
}

























