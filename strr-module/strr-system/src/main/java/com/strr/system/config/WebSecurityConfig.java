package com.strr.system.config;

import com.strr.base.service.PermissionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    @Bean("pms")
    public PermissionService permissionService() {
        return new PermissionService();
    }
}
