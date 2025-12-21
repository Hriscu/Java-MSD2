package org.example.demo.config;

import org.example.demo.security.AppUserDetailsService;
import org.example.demo.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AppUserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(AppUserDetailsService userDetailsService,
                          JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
        return builder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // login & register public
                        .antMatchers("/login", "/register").permitAll()
                        // grades public (to show http://localhost:8081/api/grades/upload-csv)
                        .antMatchers("/api/grades/**").permitAll()
                        // public (to show http://localhost:8081/api/integration/solve-pack/1)
                        // public (to show http://localhost:8081/api/instructor-prefs/OPT-ML?compulsoryAbbr=JAVA&weight=0.8)
                        .antMatchers("/api/instructor-prefs/**").permitAll()
                        .antMatchers("/api/integration/**").permitAll()
                        // Swagger / OpenAPI public
                        .antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // actuator health & info public
                        .antMatchers("/actuator/health", "/actuator/info").permitAll()
                        // metrics with auth (ex. role ADMIN)
                        .antMatchers("/actuator/metrics/**").hasRole("ADMIN")
                        // GET on API public
                        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                        // POST/PUT/DELETE protected - roles verified with @PreAuthorize
                        .antMatchers(HttpMethod.POST, "/api/**").authenticated()
                        .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
                        .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                        // all authenticated
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
