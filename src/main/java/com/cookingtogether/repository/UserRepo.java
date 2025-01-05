package com.cookingtogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cookingtogether.hibernate.User;

/**
 * Репозиторий для работы с сущностью {@link User}.
 * Предоставляет методы для выполнения операций CRUD и взаимодействия с базой данных.
 */
public interface UserRepo extends JpaRepository<User, Long> {
}
