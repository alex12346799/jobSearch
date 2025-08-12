//package com.example.jobsearch.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import javax.sql.DataSource;
//
//@Configuration
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final PasswordEncoder encoder;
//    private final DataSource dataSource;
//
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//       String userQuery = "select username, password, enabled\n" +
//               "from USERS\n" +
//               "where username = ?;";
//        String roleQuery = "select username, concat('ROLE_', role_name) as role\n" +
//                "from USERS us, ROLES r\n" +
//                "where us.USERNAME=?\n" +
//                "and us.ROLE_ID = r.ID";
/// /        String userQuery = "SELECT email, password, enabled " +
/// /                "FROM USERS " +
/// /                "WHERE email = ?;";
/// /        String roleQuery = "select email, concat('ROLE_', role_name) as role" +
/// /                "from USERS us, ROLES r" +
/// /                "where us.email=?" +
/// /                "and us.ROLE_ID = r.ID";
//
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(userQuery)
//                .authoritiesByUsernameQuery(roleQuery);
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//     http
//             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//             .httpBasic(Customizer.withDefaults())
//             .formLogin(AbstractHttpConfigurer::disable)
//             .logout(AbstractHttpConfigurer::disable)
//             .csrf(AbstractHttpConfigurer::disable)
//             .authorizeHttpRequests(
//                     authorize -> authorize
//                     .requestMatchers(HttpMethod.POST, "/resumes").hasRole("APPLICANT")
//                     .requestMatchers(HttpMethod.POST, "/vacancies").hasRole("EMPLOYEE")
//                     .anyRequest().permitAll()
//                     );
//     return http.build();
//    }
//}

package com.example.jobsearch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final PasswordEncoder encoder;
    private final DataSource dataSource;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        String userQuery = "select username, password, enabled\n" +
//                "from USERS\n" +
//                "where username = ?;";
//        String roleQuery = "select username, concat('ROLE_', role_name) as role\n" +
//                "from USERS us, ROLES r\n" +
//                "where us.USERNAME=?\n" +
//                "and us.ROLE_ID = r.ID";
//        String userQuery = "SELECT email, password, enabled " +
//                "FROM USERS " +
//                "WHERE email = ?;";
//        String roleQuery = "select email, concat('ROLE_', role_name) as role" +
//                "from USERS us, ROLES r" +
//                "where us.email=?" +
//                "and us.ROLE_ID = r.ID";
        String userQuery = "select email, password, enabled " +
                "from USERS " +
                "where email = ?;";

        String roleQuery = "select email, concat('ROLE_', role_name) as role " +
                "from USERS us, ROLES r " +
                "where us.EMAIL = ? " +
                "and us.ROLE_ID = r.ID";
//        String roleQuery = "select EMAIL, ROLE_NAME\n" +
//                "from USER_TABLE ut,\n" +
//                "     ROLES r\n" +
//                "where ut.EMAIL = ?\n" +
//                "  and ut.ROLE_ID = r.ID;";


        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(userQuery)
                .authoritiesByUsernameQuery(roleQuery);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers(HttpMethod.GET, "/vacancies").hasRole("EMPLOYEE")
                                .requestMatchers(HttpMethod.POST, "/vacancies").hasRole("EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/resumes").hasAnyRole("EMPLOYEE", "APPLICANT")
                                .requestMatchers(HttpMethod.POST, "/resumes").hasRole("APPLICANT")
                                .anyRequest().permitAll()
                );
        return http.build();
    }
}

