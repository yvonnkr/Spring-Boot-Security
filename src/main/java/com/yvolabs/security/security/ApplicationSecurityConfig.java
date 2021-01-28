package com.yvolabs.security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** Basic Auth example
         * -> will perform a basic auth from a web browser a popup window(modal) -> username/password
         * -> Or if using eg. postman /react app -> set request headers -> Authorization    Basic ***the user&password as base64****
         *  Con: No way to logout
         */
        http
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails johnDoeUser = User.builder()
                .username("johndoe")
                .password("$$123456")
                .roles("STUDENT") //ROLE_STUDENT
                .build();

        return new InMemoryUserDetailsManager(johnDoeUser);
    }
}
