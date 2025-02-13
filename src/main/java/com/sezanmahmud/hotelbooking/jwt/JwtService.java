package com.sezanmahmud.hotelbooking.jwt;

import com.sezanmahmud.hotelbooking.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Autowired
    private TokenRepository tokenRepository;


    private final String SECURITY_KEY="";
}
