package com.venkat.config;

import com.venkat.exception.ContentAPIRequestException;
import com.venkat.model.User;
import com.venkat.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
this class will validate the token, each request will have
the token as header,
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        logger.info("doFilterInternal is invoked to authenticate  the endpoint ");

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;
        //if request is not having header, request will not be processed
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            jwtToken = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwtToken);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = this.userService.getUserByEmail(userEmail);
                if(user != null) {
                    if (jwtService.validateToken(jwtToken, user)) {
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority(user.getRole().name()));
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(userEmail, null, authorities);
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);//put token in SecurityContextHolder for logged-in user
                    }
                }else{
                    throw new ContentAPIRequestException("No user found in database");
                }
            }

        }catch(ExpiredJwtException ex){
            // Handle JWT token expiration
            request.setAttribute("expired", "JWT token is expired"); // Set the exception as a request attribute
        }catch (JwtException ex){
            // Handle JWT token tampering
            request.setAttribute("exception", "JWT token is not valid, please login again"); // Set the exception as a request attribute
        }
        //this is for next filter if any or else this request goes to
        //Dispatcher servlet to Controller end point to process and gives the
        //response
        filterChain.doFilter(request, response);
    }
}
