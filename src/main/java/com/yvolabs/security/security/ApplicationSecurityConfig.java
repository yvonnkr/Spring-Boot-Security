package com.yvolabs.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.yvolabs.security.security.ApplicationUserPermission.*;
import static com.yvolabs.security.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /** Basic Auth example
         * -> will perform a basic auth from a web browser a popup window(modal) -> username/password
         * -> Or if using eg. postman /react app -> set request headers -> Authorization    Basic ***the user&password as base64****
         *  Con: No way to logout
         */
        http
                .csrf().disable() // TODO: More on csrf later
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.GET,"/management/api/**").hasAnyRole(ADMIN.name(),ADMIN_TRAINEE.name())
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
                .password(passwordEncoder.encode("$$123456"))
                .roles(STUDENT.name()) //ROLE_STUDENT
                .build();

        UserDetails adminUser = User.builder()
                .username("adminuser")
                .password(passwordEncoder.encode("$$654321"))
                .roles(ADMIN.name()) //ROLE_ADMIN
                .build();

        UserDetails adminTraineeUser = User.builder()
                .username("admintraineeuser")
                .password(passwordEncoder.encode("$$654321"))
                .roles(ADMIN_TRAINEE.name()) // ROLE_ADMIN_TRAINEE
                .build();

        return new InMemoryUserDetailsManager(johnDoeUser, adminUser, adminTraineeUser);
    }
}
