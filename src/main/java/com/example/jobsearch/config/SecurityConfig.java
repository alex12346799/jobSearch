package com.example.jobsearch.config;
import com.example.jobsearch.utils.RedirectHelper;
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

    private final RedirectHelper redirectHelper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .httpBasic(Customizer.withDefaults())
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login?error=true")
//
//                                .successHandler((request, response, authentication) -> {
//                                    var roles = authentication.getAuthorities()
//                                            .stream()
//                                            .map(a -> a.getAuthority())
//                                            .toList();
//                                    if (roles.contains("EMPLOYEE")) {
//                                        response.sendRedirect("/resumes");
//                                    } else if (roles.contains("APPLICANT")) {
//                                        response.sendRedirect("/vacancies");
//                                    }else {
//                                        response.sendRedirect("/login");
//                                    }
//                                })
                                .successHandler((request, response, authentication) -> {
                                    redirectHelper.redirectByRole(authentication.getAuthorities(), response);
                                })


//                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .authorizeHttpRequests(
                        authorize -> authorize
                                .requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/vacancies").hasAnyAuthority("EMPLOYEE", "APPLICANT")
                                .requestMatchers("/vacancies/create").hasAuthority("EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/resumes").hasAnyAuthority("EMPLOYEE")
                                .requestMatchers("/resumes/create").hasAuthority("APPLICANT")
                                .anyRequest().permitAll()
                );
        return http.build();
    }
}

