package com.venkat.service;

import com.venkat.exception.ContentAPIRequestException;
import com.venkat.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
custom authentication which will authenticate requests based on a key present in the header
of the request valid application key is stored in app configurations.

1. Security Config wire up the authentication filter in the spring context
2. Authentication Filter intercept the request , create authentication object and
pass it to authentication manager
3. Authentication provider perform evaluation to decide whether to mark the request
authorized or unauthorized
4. Authentication Object contains authentication status authorized(true)
unauthorized(true)
 */

//this class is used to check logged-in user credential with database record for
//whom he claims and invoked from authentication manager
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("Authenticating the user ");
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        //loading the user for given email from the database
        User user = userService.getUserByEmail(email);
        if(user != null){
            if(passwordEncoder.matches(password, user.getPassword())){
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
                return new UsernamePasswordAuthenticationToken(email, password, authorities);
            }else{
                throw new ContentAPIRequestException("email/password is wrong");
            }
        }else{
            throw new ContentAPIRequestException("No user found");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
