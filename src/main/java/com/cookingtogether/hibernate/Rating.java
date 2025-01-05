package com.cookingtogether.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Класс, представляющий рейтинг рецепта.
 * Используется для хранения информации о рейтингах рецептов, таких как идентификаторы рецепта и пользователя, а также оценка.
 */
@Entity
@Table(name = "ratings")
public class Rating {

    /** Уникальный идентификатор рейтинга. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Идентификатор рецепта, которому был поставлен рейтинг. */
    private int recipeId;

    /** Идентификатор пользователя, который поставил рейтинг. */
    private int userId;

    /** Оценка, поставленная пользователем рецепту. */
    private float score;

    /**
     * Конструктор для создания объекта рейтинга.
     *
     * @param id        уникальный идентификатор рейтинга.
     * @param recipeId  идентификатор рецепта.
     * @param userId    идентификатор пользователя.
     * @param score     оценка, поставленная пользователем рецепту.
     */
    public Rating(int id, int recipeId, int userId, float score) {
        this.id = id;
        this.recipeId = recipeId;
        this.userId = userId;
        this.score = score;
    }

    /**
     * Возвращает идентификатор рейтинга.
     *
     * @return уникальный идентификатор рейтинга.
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор рейтинга.
     *
     * @param id уникальный идентификатор рейтинга.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает идентификатор рецепта, которому был поставлен рейтинг.
     *
     * @return идентификатор рецепта.
     */
    public int getRecipeId() {
        return recipeId;
    }

    /**
     * Устанавливает идентификатор рецепта, которому будет поставлен рейтинг.
     *
     * @param recipeId идентификатор рецепта.
     */
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    /**
     * Возвращает идентификатор пользователя, который поставил рейтинг.
     *
     * @return идентификатор пользователя.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Устанавливает идентификатор пользователя, который ставит рейтинг.
     *
     * @param userId идентификатор пользователя.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Возвращает оценку, поставленную пользователем рецепту.
     *
     * @return оценка (в формате float).
     */
    public float getScore() {
        return score;
    }

    /**
     * Устанавливает оценку, которую пользователь ставит рецепту.
     *
     * @param score оценка (в формате float).
     */
    public void setScore(float score) {
        this.score = score;
    }
}
