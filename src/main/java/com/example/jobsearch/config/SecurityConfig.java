package com.example.jobsearch.config;

import com.example.jobsearch.auth.security.RestAccessDeniedHandler;
import com.example.jobsearch.auth.security.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            RestAuthenticationEntryPoint authenticationEntryPoint,
            RestAccessDeniedHandler accessDeniedHandler,
            JwtAuthenticationConverter jwtAuthenticationConverter
    ) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .logout(logout -> logout.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/images/**", "/css/**", "/js/**").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/api/v1/auth/register/applicant",
                                "/api/v1/auth/register/employer",
                                "/api/v1/auth/login",
                                "/api/v1/auth/refresh",
                                "/api/v1/auth/password/forgot",
                                "/api/v1/auth/password/reset").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/vacancies/**")
                        .hasAnyAuthority("EMPLOYER", "APPLICANT", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/vacancies")
                        .hasAnyAuthority("EMPLOYER", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/vacancies/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/vacancies/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/vacancies").hasAnyAuthority("EMPLOYER", "APPLICANT")
                        .requestMatchers("/vacancies/create").hasAuthority("EMPLOYER")
                        .requestMatchers(HttpMethod.GET, "/resumes").hasAnyAuthority("EMPLOYER", "APPLICANT")
                        .requestMatchers("/resumes/create").hasAuthority("APPLICANT")
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("role");
        authoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        converter.setPrincipalClaimName("email");
        return converter;
    }
}
