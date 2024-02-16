package com.venkat.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {//spring security, each request is intercepted by this class

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    //this interface implementation is CustomAuthenticationProvider
    @Autowired
    private AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("securityFilterChain is called");
        //authentication
        http
            //.cors(Customizer.withDefaults())//by default use a bean by the name of corsConfigurationSource
            //.cors(c -> c.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> {
                request.requestMatchers("/api/auth/**")
                        .permitAll();
                request.requestMatchers("/actuator/**")
                        .permitAll();
                request.requestMatchers(CorsUtils::isPreFlightRequest)
                        .permitAll();
                request.anyRequest()
                        .authenticated();
            }).sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            ).authenticationProvider(authenticationProvider) //will check user credentials(password comparison)
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exceptionHandling -> exceptionHandling
                    .authenticationEntryPoint(customAuthenticationEntryPoint())
            );
        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint customAuthenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    private static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
        private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response,
                             org.springframework.security.core.AuthenticationException authException)
                throws IOException, ServletException {

            logger.error("Responding with unauthorized error. Message - {}", authException.getMessage());
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //key, which is set in JwtAuthenticationFilter class
            String exception = (String)request.getAttribute("exception");
            if(exception != null){
                response.getOutputStream()
                        .println("{ \"error\": \"Unauthorized\", \"message\": \"" + exception + "\" }");
            }else {
                response.getOutputStream()
                        .println("{ \"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\" }");
            }
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));//GET,POST,...
        configuration.setAllowedHeaders(Arrays.asList("*")); //Authorization
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
