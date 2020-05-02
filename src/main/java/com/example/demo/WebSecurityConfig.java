package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User userAdmin = new User("Marcin",
                getPasswordEncoder().encode("ddd"),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));

        User userUser = new User("Paweł",
                getPasswordEncoder().encode("qqq"),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        auth.inMemoryAuthentication().withUser(userAdmin);
        auth.inMemoryAuthentication().withUser(userUser);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/getAdmin").hasRole("ADMIN")
                                .antMatchers("/getUser").hasAnyRole("USER","ADMIN")
                                .antMatchers("/getAll").permitAll()
                                .and()
                                .formLogin().permitAll()
                                .and()
                                .logout().logoutSuccessUrl("/getLogout");
    }
}
