package ru.user.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * конфигурация безопасности для доступа к технологическим страницам
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${myapp.application.adminUserPassword}")
    private String adminUserPassword;

    private final PasswordEncoder passwordEncoder;

    private static final String[] AUTH_PRIVATE = {
            "/private/**"
    };

    private static final String[] AUTH_WHITELIST = {
            "/api/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**",
            "/static/**",
            "/public/**",

            "/error",
            "/actuator/**"
    };

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode(adminUserPassword))
                .roles("ADMIN");
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.GET, AUTH_PRIVATE)
                .access("isFullyAuthenticated() and hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.POST, AUTH_PRIVATE)
                .access("isFullyAuthenticated() and hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated().and().formLogin();
    }
}