package com.cookingtogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cookingtogether.Rating;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Rating} в базе данных.
 * Использует Spring Data JPA для реализации CRUD операций.
 * <p>
 * Предоставляет методы для получения и управления рейтингами рецептов.
 * </p>
 * 
 * @see Rating
 * @see JpaRepository
 */
@Repository
public interface RatingRepo extends JpaRepository<Rating, Integer> {

    /**
     * Найти рейтинг по ID рецепта и ID пользователя.
     * Этот метод используется для получения рейтинга конкретного рецепта, поставленного определённым пользователем.
     *
     * @param recipeId идентификатор рецепта.
     * @param userId   идентификатор пользователя.
     * @return объект {@link Rating}, если найден рейтинг, иначе {@link Optional#empty()}.
     */
    Optional<Rating> findByRecipeIdAndUserId(int recipeId, int userId);

    /**
     * Найти все рейтинги по ID рецепта.
     * Этот метод используется для получения всех рейтингов, связанных с конкретным рецептом.
     *
     * @param recipeId идентификатор рецепта.
     * @return список {@link Rating}, связанных с указанным рецептом.
     */
    List<Rating> findByRecipeId(int recipeId);
}
