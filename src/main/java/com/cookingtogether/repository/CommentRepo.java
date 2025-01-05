package com.cookingtogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cookingtogether.hibernate.Comment;

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
}
