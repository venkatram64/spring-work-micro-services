package com.venkat.service;

import com.venkat.exception.ContentAPIRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    //CustomUserDetailService implements the UserDetailsService
    private UserDetailsService userDetailService;
    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailService, PasswordEncoder passwordEncoder){
        this.userDetailService = userDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("Authenticating the user ");
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        //loading the user for given email from the database
        UserDetails userDetails = userDetailService.loadUserByUsername(email);
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            //Authentication failed
            //throw new BadCredentialsException("Username or Password wrong");
            throw new ContentAPIRequestException("Username or password is incorrect");
        }
        //Authentication successful
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
