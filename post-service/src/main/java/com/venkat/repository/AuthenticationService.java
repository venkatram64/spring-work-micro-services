package com.venkat.repository;

import com.venkat.config.JwtService;
import com.venkat.model.Role;
import com.venkat.model.User;
import com.venkat.vo.AuthRequest;
import com.venkat.vo.AuthResponse;
import com.venkat.vo.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager manager;

    public AuthResponse register(UserRequest request){//create user

        var user = new User(request.firstName(),request.lastName(),
                    request.email(),passwordEncoder.encode(request.password()), Role.USER);
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(AuthRequest request){//login user

        manager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userRepository.findByEmail(request.email())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

}
