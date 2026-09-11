package com.sensei.config;

import com.sensei.entity.User;
import com.sensei.entity.UserRole;
import com.sensei.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/registration","/error").permitAll()
                .antMatchers("/account/**").hasAnyRole(UserRole.USER.name(),UserRole.ADMIN.name(),UserRole.SUPER_ADMIN.name())
                .antMatchers("/admin", "/admin/**").hasAnyRole(UserRole.ADMIN.name(),UserRole.SUPER_ADMIN.name())
                .antMatchers("/super-admin/**").hasRole(UserRole.SUPER_ADMIN.name())
                .and().formLogin().loginPage("/login").permitAll().usernameParameter("email").defaultSuccessUrl("/account").failureUrl("/login?error")
                .and().logout().logoutUrl("/logout").permitAll()
                .and().build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.
                        findByEmailIgnoreCase(username).
                        orElseThrow(()-> new UsernameNotFoundException("User with email = "+ username + " not found"));

                Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getUserRole().toAuthority());
                return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),roles);
            }
        };
    }


}
