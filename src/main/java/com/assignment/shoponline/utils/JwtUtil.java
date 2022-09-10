package com.assignment.shoponline.utils;

import com.assignment.shoponline.entity.Account;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil { //sinh token theo role
    public static Algorithm algorithm;
    public static JWTVerifier verifier;
    private static final String JWT_SECRET_KEY = "SD23xbts2345dsgfsdagSDFGDFG";
    private static final int EXPIRED_TIME = 60 * 60; //1 hour
    public static final String ROLE_CLAIM_KEY = "role";
    private static final String DEFAULT_ISSUER = "T2009M1";

    public static Algorithm getAlgorithm() {
        if (null == algorithm) {
            algorithm = Algorithm.HMAC256(JWT_SECRET_KEY.getBytes());
        }
        return algorithm;
    }

    public static JWTVerifier getVerifier() {
        if (verifier == null) {
            verifier = JWT.require(getAlgorithm()).build();
        }
        return verifier;
    }

    public static DecodedJWT getDecodedJwt(String token) {
        DecodedJWT decodedJWT = getVerifier().verify(token);
        return decodedJWT;
    }

    public static String generateToken(String subject, String role, String issuer) {
        if (null == role || role.length() == 0) {
            return JWT.create()
                    .withSubject(subject)
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRED_TIME*1000))
                    .withIssuer(issuer)
                    .sign(getAlgorithm());
        }
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRED_TIME*1000))
                .withIssuer(issuer)
                .withClaim(JwtUtil.ROLE_CLAIM_KEY, role)
                .sign(getAlgorithm());
    }

    public static String generateTokenByAccount(Account account) {
        return JWT.create()
                .withSubject(String.valueOf(account.getId()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRED_TIME*1000))
                .withIssuer(DEFAULT_ISSUER)
                .withClaim(JwtUtil.ROLE_CLAIM_KEY, account.getRole() == Enums.Role.ADMIN ? "ADMIN":"USER")
                .withClaim("userName", account.getUserName())
                .sign(getAlgorithm());
    }
}
