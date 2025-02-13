package com.cookingtogether.service;

import com.cookingtogether.User;
import com.cookingtogether.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Реализация {@link UserDetailsService} для загрузки данных пользователя.
 * <p>
 * Этот сервис используется для загрузки данных пользователя для аутентификации в Spring Security.
 * Он обращается к {@link UserRepo} для поиска пользователя по имени и возвращает объект {@link UserDetails}.
 * </p>
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepo userRepo;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param userRepo репозиторий для работы с сущностью {@link User}.
     */
    public UserDetailsServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Загрузить пользователя по имени.
     *
     * @param username имя пользователя.
     * @return объект {@link UserDetails}, содержащий информацию о пользователе.
     * @throws UsernameNotFoundException если пользователь с заданным именем не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Поиск пользователя по имени в репозитории
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND: " + username));

        // Возвращение объекта UserDetails для аутентификации
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
