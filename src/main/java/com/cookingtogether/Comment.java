package com.cookingtogether;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "recipe", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

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
    public Comment(int id, Recipe recipe, User user, String commentText) {
        this.id = id;
        this.recipe = recipe;
        this.user = user;
        this.commentText = commentText;
    }
    
    public Comment() {
    	this.id = 0;
        this.commentText = "";
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
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Устанавливает идентификатор рецепта, к которому относится комментарий.
     *
     * @param recipeId идентификатор рецепта.
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Возвращает идентификатор пользователя, который оставил комментарий.
     *
     * @return идентификатор пользователя.
     */
    public User getUser() {
        return user;
    }

    /**
     * Устанавливает идентификатор пользователя, который оставил комментарий.
     *
     * @param userId идентификатор пользователя.
     */
    public void setUser(User user) {
        this.user = user;
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
