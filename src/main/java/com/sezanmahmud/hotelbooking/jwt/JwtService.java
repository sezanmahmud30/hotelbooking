package com.sezanmahmud.hotelbooking.jwt;

import com.sezanmahmud.hotelbooking.entity.User;
import com.sezanmahmud.hotelbooking.repository.TokenRepository;
import io.jsonwebtoken.Claims;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Autowired
    private TokenRepository tokenRepository;


    private final String SECURITY_KEY = "9cyt4n3RLaihXJcBmXCRWtMg0AsVwjcTzaCtQDxSaSezan30Ap34fdD6HNoc9z8x";

    //get all parts from token

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getsigninKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }


    //Security key ke encript kore return korbe

    private SecretKey getsigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECURITY_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }


    // token generate korbe

    public String generateToken(User user){
        return Jwts
                .builder()
                .setSubject(user.getEmail())                                            //Set Email as Subject
                .claim("role", user.getRole())                                       // Add User Role to Payload
                .setIssuedAt(new Date(System.currentTimeMillis()))                      // Set Token Issue time
                .setExpiration(new Date(System.currentTimeMillis()+24*60*60*1000))      //Set Token Expire Time
                .signWith(getsigninKey())                                               // Sign the Token with Secret key
                .compact();                                                             // Build and compacts the token into String
    }



}