package ru.user.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.user.service.MyUserDetailsService;

/**
 * конфигурация безопасности для доступа к технологическим страницам
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Value("${myapp.application.adminUserPassword}")
//    private String adminUserPassword;

    private final PasswordEncoder passwordEncoder;
    private final MyUserDetailsService myUserDetailsService;

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

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService());
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return myUserDetailsService;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder.encode(adminUserPassword))
//                .roles("ADMIN");
//    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers(HttpMethod.GET, AUTH_PRIVATE)
                .access("isFullyAuthenticated() and hasRole('ROLE_USER')")
                .antMatchers(HttpMethod.POST, AUTH_PRIVATE)
                .access("isFullyAuthenticated() and hasRole('ROLE_USER')")
                .antMatchers(HttpMethod.PUT, AUTH_PRIVATE)
                .access("isFullyAuthenticated() and hasRole('ROLE_USER')")
                .antMatchers(HttpMethod.DELETE, AUTH_PRIVATE)
                .access("isFullyAuthenticated() and hasRole('ROLE_USER')")
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
    }
}