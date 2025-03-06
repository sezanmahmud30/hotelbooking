package com.sezanmahmud.hotelbooking.service;


import com.sezanmahmud.hotelbooking.entity.AuthenticationResponse;
import com.sezanmahmud.hotelbooking.entity.Role;
import com.sezanmahmud.hotelbooking.entity.Token;
import com.sezanmahmud.hotelbooking.entity.User;
import com.sezanmahmud.hotelbooking.jwt.JwtService;
import com.sezanmahmud.hotelbooking.repository.TokenRepository;
import com.sezanmahmud.hotelbooking.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService ;
    private final TokenRepository tokenRepository ;
    private final AuthenticationManager authenticationManager ;
    private final EmailService emailService ;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepository tokenRepository, AuthenticationManager authenticationManager, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    private void saveUserToken(String jwt, User user) {
        Token token=new Token();
        token.setToken(jwt);
        token.setLogout(false);
        token.setUser(user);

        tokenRepository.save(token);

    }

    private void removeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokenByUser(user.getId());

        if (validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(token -> {
            token.setLogout(true);
        });

        tokenRepository.saveAll(validTokens);


    }

    public AuthenticationResponse register(User user){

        // We chack that Already any user excit with this Email

        if(userRepository.findByEmail(user.getEmail()).isPresent()){

            return new AuthenticationResponse(null,"User Already Exists");
        }

        // Encode User Password to save DB
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.valueOf("USER"));
        user.setLock(true);
        user.setActive(false);

        userRepository.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);


        sendActivationEmail(user);

       return new AuthenticationResponse(jwt,"User Registation was Successful");
    }

    private void sendActivationEmail(User user) {

        String activationLink = "http://localhost:8087/active/" + user.getId();
        String mailText= """
                
                
                
                """
    }


}
