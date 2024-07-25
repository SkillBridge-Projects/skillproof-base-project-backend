package com.skillproof.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 7_200_000; //2 Hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTH = "Authorization";
    public static final String EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String ALLOW_HEADERS = "Access-Control-Allow-Headers";
    public static final String ALLOW_HEADERS_VALUE = "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header";
    public static final String CONTENT_TYPE = "Authorization";
}