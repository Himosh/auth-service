//package com.miniproject.config;
//
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
//
//    private static final String REALM_ACCESS_CLAIM = "realm_access";
//    private static final String ROLES_CLAIM = "roles";
//
//    @Override
//    public Collection<GrantedAuthority> convert(Jwt jwt) {
//        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get(REALM_ACCESS_CLAIM);
//
//        if (realmAccess == null || realmAccess.isEmpty()) {
//            return List.of();
//        }
//
//        Collection<String> roles = (Collection<String>) realmAccess.get(ROLES_CLAIM);
//
//        return roles.stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                .collect(Collectors.toList());
//    }
//}
