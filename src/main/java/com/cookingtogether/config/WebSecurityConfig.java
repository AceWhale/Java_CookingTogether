package com.cookingtogether.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;

import com.cookingtogether.repository.UserRepo; // Репозиторий для пользователей

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserRepo userRepo;

    public WebSecurityConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Убедитесь, что используете BCrypt
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            	.requestMatchers("/create", "/blogs/create/**", "/blogs/{slug}/comment").authenticated()
                .requestMatchers("/**").permitAll() // Права доступа
                .anyRequest().authenticated() // Все остальные страницы требуют авторизации
            .and()
            .formLogin()
                .loginPage("/auth/login") // Страница логина
                .loginProcessingUrl("/login") // URL, по которому будет отправляться форма логина
                .defaultSuccessUrl("/", true) // Страница, куда перенаправляется после успешного входа
                .failureUrl("/auth/login?error=true") // URL для отображения ошибки
                .permitAll()
            .and()
            .logout()
            	.logoutUrl("/logout")  // URL для выхода
            	.logoutSuccessUrl("/") // Перенаправление после выхода
            	.invalidateHttpSession(true)  // Очищаем сессию при выходе
            	.clearAuthentication(true)   // Очищаем информацию об авторизации
            	.permitAll();
        return http.build();
    }

    // Добавьте этот метод для настройки AuthenticationManager
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}
