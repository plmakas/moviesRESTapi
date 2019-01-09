package com.plmakas.movies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class RESTSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/v1/movies").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v1/comments").hasRole("USER")
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user1 =
                User.builder()
                    .username("moviefan_admin")
                    .password(passwordEncoder().encode("password"))
                    .roles("ADMIN","USER")
                    .build();

        UserDetails user2 =
                User.builder()
                    .username("moviefan_user")
                    .password(passwordEncoder().encode("password"))
                    .roles("USER")
                    .build();

        UserDetails user3 =
                User.builder()
                    .username("moviefan_none")
                    .password(passwordEncoder().encode("password"))
                    .roles("NONE")
                    .build();

        return new InMemoryUserDetailsManager(user1, user2, user3);
    }
}
