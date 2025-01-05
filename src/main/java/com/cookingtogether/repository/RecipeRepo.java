package com.cookingtogether.repository;

import com.cookingtogether.hibernate.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностью Recipe.
 * Этот репозиторий предоставляет методы для взаимодействия с базой данных.
 */
@Repository
public interface RecipeRepo extends JpaRepository<Recipe, Integer> {

    /**
     * Найти рецепты по заголовку.
     *
     * @param title Заголовок рецепта.
     * @return Список рецептов, соответствующих заголовку.
     */
    List<Recipe> findByTitle(String title);

    /**
     * Найти рецепты по категории.
     *
     * @param category Категория рецепта.
     * @return Список рецептов, соответствующих категории.
     */
    List<Recipe> findByCategory(String category);

    /**
     * Найти рецепты, созданные пользователем.
     *
     * @param userId Идентификатор пользователя.
     * @return Список рецептов, созданных указанным пользователем.
     */
    List<Recipe> findByUserId(int userId);
}
