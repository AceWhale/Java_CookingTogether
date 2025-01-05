package com.cookingtogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cookingtogether.hibernate.Rating;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью Rating в базе данных.
 * Использует Spring Data JPA для реализации CRUD операций.
 */
@Repository
public interface RatingRepo extends JpaRepository<Rating, Integer> {

    /**
     * Найти все рейтинги по ID рецепта.
     * Этот метод можно использовать для поиска всех рейтингов по конкретному рецепту.
     */
	
	Optional<Rating> findByRecipeIdAndUserId(int recipeId, int userId);
    List<Rating> findByRecipeId(int recipeId);
}
