package com.cookingtogether.service;

import com.cookingtogether.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    /**
     * Конструктор, который инжектирует репозиторий пользователей.
     * 
     * @param userRepo репозиторий для доступа к данным пользователей.
     */
    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Загружает пользователя по имени пользователя для аутентификации.
     *
     * @param username имя пользователя.
     * @return объект, реализующий UserDetails, если пользователь найден.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ищем пользователя в базе данных по имени пользователя.
        return userRepo.findByUsername(username)
                // Если пользователь не найден, выбрасываем исключение.
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
