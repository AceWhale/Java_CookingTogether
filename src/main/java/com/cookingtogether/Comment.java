package com.cookingtogether;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Класс, представляющий комментарий к рецепту.
 * Используется для хранения информации о комментариях, таких как текст комментария, 
 * идентификатор рецепта и пользователя.
 */
@Entity
@Table(name = "comments")
public class Comment {

    /** Уникальный идентификатор комментария. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Идентификатор рецепта, к которому относится комментарий. */
    private int recipeId;

    /** Идентификатор пользователя, который оставил комментарий. */
    private int userId;

    /** Текст комментария. */
    private String commentText;

    /**
     * Конструктор для создания объекта комментария.
     *
     * @param id          уникальный идентификатор комментария.
     * @param recipeId    идентификатор рецепта.
     * @param userId      идентификатор пользователя.
     * @param commentText текст комментария.
     */
    public Comment(int id, int recipeId, int userId, String commentText) {
        this.id = id;
        this.recipeId = recipeId;
        this.userId = userId;
        this.commentText = commentText;
    }

    /**
     * Возвращает идентификатор комментария.
     *
     * @return уникальный идентификатор комментария.
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор комментария.
     *
     * @param id уникальный идентификатор комментария.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает идентификатор рецепта, к которому относится комментарий.
     *
     * @return идентификатор рецепта.
     */
    public int getRecipeId() {
        return recipeId;
    }

    /**
     * Устанавливает идентификатор рецепта, к которому относится комментарий.
     *
     * @param recipeId идентификатор рецепта.
     */
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    /**
     * Возвращает идентификатор пользователя, который оставил комментарий.
     *
     * @return идентификатор пользователя.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Устанавливает идентификатор пользователя, который оставил комментарий.
     *
     * @param userId идентификатор пользователя.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Возвращает текст комментария.
     *
     * @return текст комментария.
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Устанавливает текст комментария.
     *
     * @param commentText текст комментария.
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
