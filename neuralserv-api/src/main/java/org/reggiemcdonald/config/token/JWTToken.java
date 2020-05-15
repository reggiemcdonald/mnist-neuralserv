package org.reggiemcdonald.config.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.reggiemcdonald.persistence.entity.AppUserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class made with help from
 * https://dev.to/cuongld2/create-apis-with-jwt-authorization-using-spring-boot-24f9
 */

public class JWTToken implements Token {

    @Value("${jwt.secret.string}")
    private String SECRET_STRING;
    private static final String AUTHORITIES_KEY = "AUTHORITIES";
    private long expiration;
    private final SignatureAlgorithm signatureAlgorithm;

    public JWTToken(long _expiration, SignatureAlgorithm _signatureAlgorithm) {
        expiration = _expiration;
        signatureAlgorithm = _signatureAlgorithm;
    }

    @Override
    public Boolean validate(String token, UserDetails model) {
        String username = username(token);
        Date expirationDate = expirationDate(token);
        return username.equals(model.getUsername()) && !expirationDate.before(new Date(System.currentTimeMillis()));
    }

    @Override
    public String create(UserDetails model) {
        final Date issuedAt = new Date(System.currentTimeMillis());
        final Date expiration = new Date(issuedAt.getTime() + this.expiration);
        final String authorities = model
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts
                .builder()
                .claim(AUTHORITIES_KEY, authorities)
                .setSubject(model.getUsername())
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(signatureAlgorithm, SECRET_STRING)
                .compact();
    }

    @Override
    public String username(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private Date expirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private Claims claims(String token) {
        return Jwts
                .parser()
                .setSigningKey(SECRET_STRING)
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T getClaim(String token, Function<Claims, T> res) {
        return res.apply(
                claims(token)
        );
    }
}
