//package com.miniproject.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
////    private static final String USER_ROLE = "user";
//    private static final String ADMIN_ROLE = "admin";
//
//    @Bean
//    public SecurityFilterChain resourceServerFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**","/api")
//                .permitAll());
//        return httpSecurity.build();
//    }
//
////    @Bean
////    public JwtAuthenticationConverter jwtAuthenticationConverter() {
////        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
////        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRealmRoleConverter());
////        return jwtAuthenticationConverter;
////    }
//}
