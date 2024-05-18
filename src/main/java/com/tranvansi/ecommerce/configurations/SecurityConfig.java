package com.tranvansi.ecommerce.configurations;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;

    @Value("${jwt.signerKey}")
    private String signerKey;

    private String[] getPublicPostEndpoints() {
        return new String[] {
            String.format("%s/auth/register", apiPrefix),
            String.format("%s/auth/login", apiPrefix),
            String.format("%s/auth/forgot-password", apiPrefix),
            String.format("%s/auth/reset-password", apiPrefix),
        };
    }

    private String[] getPublicGetEndpoints() {
        return new String[] {
            String.format("%s/categories", apiPrefix),
            String.format("%s/categories/{id}", apiPrefix),
            String.format("%s/brands", apiPrefix),
            String.format("%s/brands/{id}", apiPrefix),
            String.format("%s/colors", apiPrefix),
            String.format("%s/colors/{id}", apiPrefix),
            String.format("%s/sizes", apiPrefix),
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        String[] PUBLIC_POST_ENDPOINTS = getPublicPostEndpoints();
        String[] PUBLIC_GET_ENDPOINTS = getPublicGetEndpoints();
        httpSecurity.authorizeHttpRequests(
                request ->
                        request.requestMatchers(HttpMethod.POST, PUBLIC_POST_ENDPOINTS)
                                .permitAll()
                                .requestMatchers(HttpMethod.GET, PUBLIC_GET_ENDPOINTS)
                                .permitAll()
                                .anyRequest()
                                .authenticated());
        httpSecurity.oauth2ResourceServer(
                oAuth2 ->
                        oAuth2.jwt(
                                        jwtConfigurer ->
                                                jwtConfigurer
                                                        .decoder(jwtDecoder())
                                                        .jwtAuthenticationConverter(
                                                                jwtAuthenticationConverter()))
                                .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
                new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "HS512");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
