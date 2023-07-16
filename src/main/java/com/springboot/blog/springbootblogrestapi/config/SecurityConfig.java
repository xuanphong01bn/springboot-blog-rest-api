package com.springboot.blog.springbootblogrestapi.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests((authorize)->

//                authorize.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
                authorize.requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll().anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                ;
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService () {
//        UserDetails phongxuan  = User.builder().username("phongxuan").password(passwordEncoder().encode("123456") ).roles("USER").build();
//
//        UserDetails admin  = User.builder().username("admin").password(passwordEncoder().encode("admin") ).roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(phongxuan, admin);
//    }
}
