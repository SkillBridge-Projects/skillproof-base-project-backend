package com.skillproof.skillproofapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillproof.skillproofapi.repositories.UserDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private UserDao userDao;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserDao userDao) {
        this.authenticationManager = authenticationManager;
        this.userDao = userDao;
        super.setAuthenticationFailureHandler(new AuthenticationFailureHandlerCustom());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            com.skillproof.skillproofapi.model.entity.User creds = new ObjectMapper()
                    .readValue(req.getInputStream(), com.skillproof.skillproofapi.model.entity.User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {


        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        PrintWriter out = res.getWriter();
        String username = ((User) auth.getPrincipal()).getUsername();
        com.skillproof.skillproofapi.model.entity.User user = userDao.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException(username));
        user.setPassword(null);
        String userJsonString = new ObjectMapper().writeValueAsString(user);
        out.print(userJsonString);
        out.flush();
    }
}
