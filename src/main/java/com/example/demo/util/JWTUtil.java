package com.example.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JWTUtil {

    private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);


    private static String SECRET_KEY;

    public static String createJWT(String username) {
        try {
            Instant now = Instant.now();
            Instant expiresAt = now.plusSeconds(60 * 60 * 24);
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create().withIssuer("demo").withIssuedAt(java.util.Date.from(now)).withExpiresAt(java.util.Date.from(expiresAt)).withClaim("username", username).sign(algorithm);
        } catch (JWTCreationException e) {
            logger.error(STR."Error creating JWT: \{e.getMessage()}");
            return null;
        }
    }

    public static boolean validateJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWT.require(algorithm).withIssuer("demo").build().verify(token);
            return true;
        } catch (Exception e) {
            logger.error(STR."Error validating JWT: \{e.getMessage()}");
            return false;
        }
    }



    public static String parseJWT(String token) {
        try {
            return JWT.decode(token).getClaim("username").asString();
        } catch (Exception e) {
            logger.error(STR."Error parsing JWT: \{e.getMessage()}");
            return null;
        }
    }


    @Value("${spring.app.jwt.secret}")
    public void setSecretKey(String secretKey) {
        SECRET_KEY = secretKey;
    }

}
