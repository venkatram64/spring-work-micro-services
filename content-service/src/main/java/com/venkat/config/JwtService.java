package com.venkat.config;

import com.venkat.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {//this class is for creating the JWT token and some helper methods

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    @Value("${jwtSecret}")
    private String secreteKey;

    @Value("${expirationInMs}")
    private int expirationInMs;  //1000(milli seconds) * 60(seconds) * 60(minutes) * 10(hours) = 36000000

    public String  extractUsername(String token) {
        //Claims::getSubject  --> method reference similar to lambda function that is Function<Claim,T>
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        //Claims::getExpiration  --> method reference similar to lambda function that is Function<Claim,T>
        return extractClaim(token, Claims::getExpiration);
    }

    //generic method type is <T> return T
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secreteKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //this method is used to generate the token for given UserDetails
    public String generateToken(User user) {
        logger.info("Generating the token");
        Map<String, Object> claims = new HashMap<>(); //empty claims
        String authorities = user.getRole().name();
        return createToken(claims, user.getEmail(), authorities);
    }

    public String createToken(Map<String, Object> claims, String subject, String authorities) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String createToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationInMs))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //token will be validated
    public Boolean validateToken(String token, User user) {
        logger.info("Validating  the token");
        final String username = extractUsername(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }

}
