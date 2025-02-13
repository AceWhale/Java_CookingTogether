package com.cookingtogether.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cookingtogether.User;

/**
 * Репозиторий для работы с сущностью {@link User}.
 * Предоставляет методы для выполнения операций CRUD и взаимодействия с базой данных.
 * <p>
 * Этот интерфейс позволяет работать с пользователями, обеспечивая функции поиска и сохранения данных.
 * </p>
 * 
 * @see User
 * @see JpaRepository
 */
public interface UserRepo extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по имени пользователя (логину).
     *
     * @param username имя пользователя.
     * @return объект {@link Optional} с найденным пользователем или {@link Optional#empty()} если пользователь не найден.
     */
    Optional<User> findByUsername(String username);
}
