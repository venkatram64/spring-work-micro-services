package com.venkat.config;

import com.venkat.repository.UserRepository;
import com.venkat.service.CustomAuthenticationProvider;
import com.venkat.service.CustomUserDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
@Configuration
public class ApplicationConfig { //this class will create the Beans those are used with @Autowired

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);

    @Bean
    public UserDetailsService userDetailsService(){ //authorization
        logger.info("Creating UserDetailService");
        return new CustomUserDetailService();
    }

    //below method is mapped with the UserDetailsService, then only UserDetailsService()
    //method works with respect to the spring security
    @Bean
    public AuthenticationProvider authenticationProvider(){
        logger.info("Creating CustomAuthenticationProvider");
        CustomAuthenticationProvider authenticationProvider =
                new CustomAuthenticationProvider(userDetailsService(), passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("Creating PasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    //below method is must, to work authentication
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        logger.info("Creating AuthenticationManager");
        return config.getAuthenticationManager();
    }

}
