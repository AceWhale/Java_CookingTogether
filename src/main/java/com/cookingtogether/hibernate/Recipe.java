package com.cookingtogether.hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Column;


import java.util.List;

/**
 * Класс, представляющий рецепт в блоге.
 * Используется для хранения информации о рецепте, включая его название, ингредиенты, шаги приготовления и рейтинг.
 */
@Entity
@Table(name = "recipes")
public class Recipe {

    /** Уникальный идентификатор рецепта. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Идентификатор блога, к которому относится рецепт. */
    private int blogId;

    /** Название рецепта. */
    private String title;

    /** Массив ингредиентов рецепта. */
    @ElementCollection
    @Column(name = "ingredient")
    private List<String> ingredients;

    /** Шаги приготовления рецепта. */
    @Column(length = 1000)  // Ограничение по длине для длинных шагов
    private String steps;

    /** Рейтинг рецепта. */
    private float rating;

    /**
     * Конструктор для создания объекта рецепта.
     *
     * @param id          уникальный идентификатор рецепта.
     * @param blogId      идентификатор блога, к которому относится рецепт.
     * @param title       название рецепта.
     * @param ingredients список ингредиентов для рецепта.
     * @param steps       шаги приготовления рецепта.
     * @param rating      рейтинг рецепта.
     */
    public Recipe(int id, int blogId, String title, List<String> ingredients, String steps, float rating) {
        this.id = id;
        this.blogId = blogId;
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
        this.rating = rating;
    }

    // Геттеры и сеттеры для всех полей

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
