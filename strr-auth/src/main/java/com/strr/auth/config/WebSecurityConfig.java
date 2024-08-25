package com.strr.auth.config;

import com.strr.auth.model.LoginUserDetails;
import com.strr.system.api.RemoteUserService;
import com.strr.system.api.model.LoginUser;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {
    @DubboReference
    private RemoteUserService remoteUserService;

    /**
     * A Spring Security filter chain for authentication.q
     */
    @Bean 
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                .anyRequest().authenticated()
            )
            // Form login handles the redirect to the login page from the
            // authorization server filter chain
            .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            LoginUser user = remoteUserService.getUserInfo(username);
            if (user != null) {
                LoginUserDetails loginUser = new LoginUserDetails();
                loginUser.setId(user.getId());
                loginUser.setUsername(user.getUsername());
                loginUser.setPassword(user.getPassword());
                loginUser.setNickname(user.getNickname());
                loginUser.setResources(user.getResources());
                return loginUser;
            }
            throw new UsernameNotFoundException(String.format("User %s not found.", username));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
