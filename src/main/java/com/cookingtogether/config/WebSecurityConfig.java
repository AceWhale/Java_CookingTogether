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

/**
 * Конфигурация безопасности для веб-приложения.
 * Включает настройки авторизации, аутентификации, а также обработку логина и логаута.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserRepo userRepo;

    /**
     * Конструктор конфигурации безопасности.
     * 
     * @param userRepo Репозиторий для пользователей, используется для поиска пользователей по имени.
     */
    public WebSecurityConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Бин для создания пароля с использованием алгоритма BCrypt.
     * 
     * @return PasswordEncoder для кодирования паролей.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Убедитесь, что используете BCrypt
    }

    /**
     * Бин для создания сервиса, который загружает пользователей по имени.
     * 
     * @return UserDetailsService для работы с пользователями.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Конфигурирует безопасность HTTP, включая управление доступом, страницу логина и обработку выхода.
     * 
     * @param http Объект для настройки HTTP безопасности.
     * @return Конфигурированная цепочка безопасности.
     * @throws Exception Если возникает ошибка при настройке безопасности.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Отключаем защиту от CSRF (если необходимо)
            .authorizeRequests()
                .requestMatchers("/create", "/blogs/create/**", "/blogs/{slug}/comment").authenticated() // Требует аутентификации
                .requestMatchers("/**").permitAll() // Доступ ко всем остальным страницам открыт для всех
                .anyRequest().authenticated() // Все остальные страницы требуют авторизации
            .and()
            .formLogin()
                .loginPage("/auth/login") // Страница логина
                .loginProcessingUrl("/login") // URL для обработки логина
                .defaultSuccessUrl("/", true) // Страница для перенаправления после успешного входа
                .failureUrl("/auth/login?error=true") // Страница для отображения ошибки
                .permitAll()
            .and()
            .logout()
                .logoutUrl("/logout") // URL для выхода
                .logoutSuccessUrl("/") // Страница для перенаправления после выхода
                .invalidateHttpSession(true) // Очищаем сессию после выхода
                .clearAuthentication(true) // Очищаем информацию о пользователе
                .permitAll();
        return http.build();
    }

    /**
     * Бин для конфигурации AuthenticationManager.
     * Используется для управления аутентификацией пользователей.
     * 
     * @param http Объект для настройки HTTP безопасности.
     * @return Конфигурированный AuthenticationManager.
     * @throws Exception Если возникает ошибка при создании AuthenticationManager.
     */
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
