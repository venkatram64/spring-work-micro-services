package com.venkat.service;

import com.venkat.model.CustomUserDetail;
import com.venkat.model.User;
import com.venkat.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("fetching the user {}", username);
        Optional<User> user = userRepository.findByEmail(username);
        return user.map(CustomUserDetail::new) //method reference lambda expression
                .orElseThrow(() -> new UsernameNotFoundException("User not found in database for given email " + username));
    }
}
