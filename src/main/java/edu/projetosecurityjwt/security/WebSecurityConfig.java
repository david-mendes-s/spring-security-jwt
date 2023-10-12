package edu.projetosecurityjwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMS", "USERS")
                        .requestMatchers(HttpMethod.GET, "products").permitAll()
                        .requestMatchers("/managers").hasAnyRole("ADMS")
                        .anyRequest()
                        .authenticated())
                .headers(headers -> headers.frameOptions(f -> f.disable()))
                .csrf((csrf) -> csrf.disable())
                .cors((cors) -> cors.disable());

        return http.build();
    }
}
