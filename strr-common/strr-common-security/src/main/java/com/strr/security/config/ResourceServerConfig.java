package com.strr.security.config;

import com.strr.security.annotation.EnableCheckPermission;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * 资源服务配置
 */
@AutoConfiguration
@EnableCheckPermission
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
}
