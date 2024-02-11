package com.venkat.service;

import com.venkat.config.JwtService;
import com.venkat.exception.ContentAPIRequestException;
import com.venkat.model.CustomUserDetail;
import com.venkat.model.Role;
import com.venkat.model.User;
import com.venkat.repository.UserRepository;
import com.venkat.vo.AuthRequest;
import com.venkat.vo.AuthResponse;
import com.venkat.vo.UserRequest;
import com.venkat.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private UserRepository userRepository;


    @Autowired //from ApplicationConfig.java
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired  //from ApplicationConfig.java
    private AuthenticationManager manager;

    public AuthResponse register(UserRequest request){ //saving the user into database
        logger.info("Saving the user record");

        if(getUser(request.email())){
            throw new ContentAPIRequestException("User already exists ");
        }
        if(request.firstName().isEmpty() || request.lastName().isEmpty()
                || request.email().isEmpty() || request.password().isEmpty()){
            throw new ContentAPIRequestException("All fields should have values");
        }
        //create user, will save the user and returns the jwtToken
        var user = new User(request.firstName(),request.lastName(),
                    request.email(),passwordEncoder.encode(request.password()), Role.USER);
        user.setCreatedAt(new Date());
        user.setModifiedAt(new Date());
        //save user in database
        var dbUser = userRepository.save(user);
        //construct below object to get JWT token
        CustomUserDetail customUserDetail = new CustomUserDetail(dbUser);
        var jwtToken = jwtService.generateToken(customUserDetail);
        return new AuthResponse(jwtToken, new UserVO(dbUser.getId(), dbUser.getFirstName(), dbUser.getLastName(), dbUser.getEmail(), dbUser.getRole().name()));
    }

    public AuthResponse authenticate(AuthRequest request) {//user is login into system
        logger.info("authenticate the user");
        try {
            //this will authenticate, internally it calls the authentication provider,
            //that is CustomAuthenticationProvider.java
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.email(), request.password());
            manager.authenticate(authenticationToken);
        }catch(AuthenticationException exception){
            logger.info("Authentication failed {}", exception);
            //throw new RuntimeException("Username or password is incorrect");
            throw new ContentAPIRequestException("Username or password is incorrect");
        }

        var user = userRepository.findByEmail(request.email())
                .orElseThrow();
        CustomUserDetail customUserDetail = new CustomUserDetail(user);
        var jwtToken = jwtService.generateToken(customUserDetail);
        return new AuthResponse(jwtToken, new UserVO(user.getId(),user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole().name()));
    }

    public boolean getUser(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public AuthResponse forgotPassword(AuthRequest request) {//login user
        logger.info("Forgot password user");

        if(request.email().isEmpty() || request.password().isEmpty()){
            throw new ContentAPIRequestException("All fields should have values");
        }

        var user = userRepository.findByEmail(request.email())
                .orElseThrow();
        //set the new password
        user.setPassword(passwordEncoder.encode(request.password()));
        //update the user
        User newUser = null;
        try {
            newUser = userRepository.save(user);
        }catch (Exception e){
            throw new ContentAPIRequestException("Unable update the password");
        }
        CustomUserDetail customUserDetail = new CustomUserDetail(newUser);
        var jwtToken = jwtService.generateToken(customUserDetail);
        return new AuthResponse(jwtToken, new UserVO(newUser.getId(),newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), newUser.getRole().name()));
    }

}
