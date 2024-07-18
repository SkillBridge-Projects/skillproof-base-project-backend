package com.skillproof.skillproofapi.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.skillproof.skillproofapi.model.request.user.UserResponse;
import com.skillproof.skillproofapi.security.SecurityConstants;
import com.skillproof.skillproofapi.services.user.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final UserService userService;

    private static final String ROLE_CLAIM = "role";

    public JwtUtil(UserService userService) {
        this.userService = userService;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.SECRET.getBytes()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String createToken(String userName){
        UserResponse user = userService.getUserByEmailAddress(userName);
        return generateToken(userName, user.getRole().name());
    }

    public String generateToken(String userName, String role) {
        String token = JWT.create()
                .withSubject(userName)
                .withClaim(ROLE_CLAIM, role)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
        return SecurityConstants.TOKEN_PREFIX + token;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

