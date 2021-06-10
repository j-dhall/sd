package edu.ds.ms.security.restsecurity.filter;

import edu.ds.ms.security.restsecurity.service.SecurityService;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT Authentication Filter
 * If an 'Authorization' header is found in the HTTP Request then the token is being retrieved.
 * That token will be validated and, if successful, then user will be authenticated.
 *
 * @author dnardelli
 */
@Component
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private SecurityService securityService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpRequest, HttpServletResponse httpResponse, FilterChain filterChain)
        throws ServletException, IOException {
    	log.debug("INSIDE JWTAuthenticationFilter.doFilterInternal()");

        final String token = httpRequest.getHeader(HEADER_STRING);

        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                securityService.authenticate(token);
            } catch (Exception e) {
                logger.debug("Failed when authenticating token '{}'. Error: '{}'", token, e.getMessage());
            }
        }

        filterChain.doFilter(httpRequest, httpResponse);

    }

}