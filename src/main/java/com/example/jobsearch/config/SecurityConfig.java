package com.example.jobsearch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder encoder;
    private final DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
       String userQuery = "select username, password, enabled\n" +
               "from USERS\n" +
               "where username = ?;";
       String roleQuery = "select username, role_name\n" +
               "from USERS us, ROLES rl\n" +
               "where us.USERNAME=?\n" +
               "and us.ROLE_ID = rl.ID";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(roleQuery);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    }
}
