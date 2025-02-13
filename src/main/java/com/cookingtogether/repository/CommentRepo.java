package com.cookingtogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cookingtogether.Comment;
import com.cookingtogether.Recipe;
import com.cookingtogether.User;

import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Comment}.
 * <p>
 * Предоставляет методы для выполнения операций CRUD с комментариями.
 * </p>
 * 
 * @see Comment
 */
@Repository
public interface CommentRepo extends JpaRepository<Comment, Integer> {

    /**
     * Найти все комментарии, связанные с определённым рецептом.
     *
     * @param recipeId идентификатор рецепта.
     * @return список комментариев, связанных с данным рецептом.
     */
    List<Comment> findByRecipeId(int recipeId);

    /**
     * Найти все комментарии, созданные пользователем.
     *
     * @param userId идентификатор пользователя.
     * @return список комментариев, связанных с данным пользователем.
     */
    List<Comment> findByUserId(int userId);
    
    /**
     * Найти все комментарии, связанные с данным рецептом.
     *
     * @param recipe объект рецепта.
     * @return список комментариев, связанных с данным рецептом.
     */
    List<Comment> findByRecipe(Recipe recipe);

    /**
     * Найти все комментарии, связанные с данным пользователем.
     *
     * @param user объект пользователя.
     * @return список комментариев, созданных данным пользователем.
     */
    List<Comment> findByUser(User user);
    
    /**
     * Удалить все комментарии, связанные с определённым рецептом.
     *
     * @param recipeId идентификатор рецепта.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM Comment c WHERE c.recipe.id = :recipeId")
    void deleteByRecipeId(@Param("recipeId") int recipeId);
}
