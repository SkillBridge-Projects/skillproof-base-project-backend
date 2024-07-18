package com.skillproof.skillproofapi.security;

import com.skillproof.skillproofapi.exceptions.InvalidRequestException;
import com.skillproof.skillproofapi.services.UserDetailsServiceImpl;
import com.skillproof.skillproofapi.utils.JwtUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.skillproof.skillproofapi.security.SecurityConstants.HEADER_STRING;
import static com.skillproof.skillproofapi.security.SecurityConstants.TOKEN_PREFIX;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> EXCLUDED_PATHS = Arrays.asList("/authenticate", "/signup", "/accessToken");

    private UserDetailsServiceImpl service = null;
    private final JwtUtil jwtUtil;

    public JWTAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws IOException, ServletException {

        String path = request.getRequestURI();
        if (isExcluded(path)) {
            chain.doFilter(request, response);
            return;
        }

        if (service == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
            service = webApplicationContext.getBean(UserDetailsServiceImpl.class); //Because service cannot be injected
        }

        String header = request.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        String authorizationHeader = request.getHeader(HEADER_STRING);
        String token = null;
        String userName = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.replace("Bearer ", "");
            userName = jwtUtil.extractUsername(token);
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = service.loadUserByUsername(userName);
            if (jwtUtil.validateToken(token, userDetails)) {
                usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                        userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            } else {
                throw new InvalidRequestException("Invalid Token");
            }
        }
        return usernamePasswordAuthenticationToken;
    }

    private boolean isExcluded(String path) {
        return EXCLUDED_PATHS.stream().anyMatch(path::startsWith);
    }
}
