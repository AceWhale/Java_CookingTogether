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
 * рецепт, к которому он относится, и пользователь, оставивший комментарий.
 */
@Entity
@Table(name = "comments")
public class Comment {

    /** Уникальный идентификатор комментария. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Рецепт, к которому относится комментарий. */
    @ManyToOne
    @JoinColumn(name = "recipe", nullable = false)
    private Recipe recipe;

    /** Пользователь, оставивший комментарий. */
    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    /** Текст комментария. */
    private String commentText;

    /**
     * Конструктор для создания нового комментария.
     *
     * @param id          уникальный идентификатор комментария.
     * @param recipe      рецепт, к которому относится комментарий.
     * @param user        пользователь, оставивший комментарий.
     * @param commentText текст комментария.
     */
    public Comment(int id, Recipe recipe, User user, String commentText) {
        this.id = id;
        this.recipe = recipe;
        this.user = user;
        this.commentText = commentText;
    }

    /**
     * Конструктор без параметров.
     * Создает пустой объект комментария.
     */
    public Comment() {
        this.id = 0;
        this.commentText = "";
    }

    /**
     * Возвращает уникальный идентификатор комментария.
     *
     * @return уникальный идентификатор комментария.
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор комментария.
     *
     * @param id уникальный идентификатор комментария.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает рецепт, к которому относится комментарий.
     *
     * @return рецепт.
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Устанавливает рецепт, к которому относится комментарий.
     *
     * @param recipe рецепт.
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Возвращает пользователя, оставившего комментарий.
     *
     * @return пользователь.
     */
    public User getUser() {
        return user;
    }

    /**
     * Устанавливает пользователя, оставившего комментарий.
     *
     * @param user пользователь.
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
