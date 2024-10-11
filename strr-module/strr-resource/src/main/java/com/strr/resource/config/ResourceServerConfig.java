package com.strr.resource.config;

import com.strr.base.service.PermissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 资源服务配置
 */
@Configuration
@EnableMethodSecurity
public class ResourceServerConfig {
    @Bean
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .securityMatcher("/**")
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer((oauth2ResourceServer) -> oauth2ResourceServer
                .jwt(Customizer.withDefaults())
            );
        // @formatter:on

        return http.build();
    }

    @Bean("pms")
    public PermissionService permissionService() {
        return new PermissionService();
    }
}
