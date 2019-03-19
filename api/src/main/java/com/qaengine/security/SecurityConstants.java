package com.qaengine.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864_000_000_000L; // 10,000 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/sign-up";

    @Value("${jwt.secret}")
    public String SECRET;
}
