package com.mycarlog.mycarlog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    private String[] publicEndpoints = {"/auth/users/register", "/auth/users/login", "/api/brands",
            "/auth/users", "/auth/users/profile/all", "/auth/users/login/changepassword", "/api/brands/{brandId}",
            "/api/brands/name={name}", "/api/brands/{brandId}/models", "/api/brands/{brandId}/models/{modelId}",
            "/api/brands/name={brandName}/models/name={modelName}", "/api/models", "/api/models/{modelId}",
            "/api/models/name={modelName}", "/api/vehicles", "/api/vehicles/{id}", "/api/brands/{brandId}/models/{modelId}/vehicles",
            "/api/brands/{brandId}/models/{modelId}/vehicles/{vehicleId}"
    };

    @Autowired
    private JWTRequestFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeRequests().antMatchers(publicEndpoints).permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public MyUserDetails myUserDetails() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
    }
}
