package com.ramazanayyildiz.EMS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity//Spring Security etkinleştirme
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf-> csrf.disable())
                //istek yetkilendirme kuralları
                .authorizeHttpRequests(authorize->authorize
                        .requestMatchers("/api/users/register").permitAll()
                        .anyRequest().authenticated())
                .httpBasic(httpBasic-> httpBasic.disable())
                .formLogin(formLogin-> formLogin.disable());
        return http.build();

    }
}
