package com.venkat.service;

import com.venkat.config.JwtService;
import com.venkat.model.CustomUserDetail;
import com.venkat.model.Role;
import com.venkat.model.User;
import com.venkat.repository.UserRepository;
import com.venkat.vo.AuthRequest;
import com.venkat.vo.AuthResponse;
import com.venkat.vo.UserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired  //from ApplicationConfig.java
    private AuthenticationManager manager;

    public AuthResponse register(UserRequest request){
        logger.info("Saving the user record");
        //create user, will save the user and returns the jwtToken
        var user = new User(request.firstName(),request.lastName(),
                    request.email(),passwordEncoder.encode(request.password()), Role.USER);
        var dbUser = userRepository.save(user);
        CustomUserDetail customUserDetail = new CustomUserDetail(dbUser);
        var jwtToken = jwtService.generateToken(customUserDetail);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(AuthRequest request){//login user
        logger.info("authenticate the user");
        try {
            //this will authenticate, internally it calls the authentication provider
            manager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        }catch(AuthenticationException exception){
            logger.info("Authentication failed {}", exception);
            return null;
        }

        var user = userRepository.findByEmail(request.email())
                .orElseThrow();
        CustomUserDetail customUserDetail = new CustomUserDetail(user);
        var jwtToken = jwtService.generateToken(customUserDetail);
        return new AuthResponse(jwtToken);
    }

}
