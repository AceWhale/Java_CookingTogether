package com.cookingtogether.hibernate;

/**
 * Класс, представляющий рецепт в блоге.
 * Используется для хранения информации о рецепте, включая его название, ингредиенты, шаги приготовления и рейтинг.
 */
public class Recipe {

    /** Уникальный идентификатор рецепта. */
    private int id;

    /** Идентификатор блога, к которому относится рецепт. */
    private int blogId;

    /** Название рецепта. */
    private String title;

    /** Массив ингредиентов рецепта. */
    private String[] ingredients;

    /** Шаги приготовления рецепта. */
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
    public Recipe(int id, int blogId, String title, String[] ingredients, String steps, float rating) {
        this.id = id;
        this.blogId = blogId;
        this.title = title;
        this.ingredients = ingredients;
        this.steps = steps;
        this.rating = rating;
    }

    /**
     * Возвращает идентификатор рецепта.
     *
     * @return уникальный идентификатор рецепта.
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор рецепта.
     *
     * @param id уникальный идентификатор рецепта.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Возвращает идентификатор блога, к которому относится рецепт.
     *
     * @return идентификатор блога.
     */
    public int getBlogId() {
        return blogId;
    }

    /**
     * Устанавливает идентификатор блога, к которому будет относиться рецепт.
     *
     * @param blogId идентификатор блога.
     */
    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    /**
     * Возвращает название рецепта.
     *
     * @return название рецепта.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает название рецепта.
     *
     * @param title название рецепта.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Возвращает список ингредиентов рецепта.
     *
     * @return массив ингредиентов.
     */
    public String[] getIngredients() {
        return ingredients;
    }

    /**
     * Устанавливает список ингредиентов для рецепта.
     *
     * @param ingredients массив ингредиентов.
     */
    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Возвращает шаги приготовления рецепта.
     *
     * @return шаги приготовления.
     */
    public String getSteps() {
        return steps;
    }

    /**
     * Устанавливает шаги приготовления рецепта.
     *
     * @param steps шаги приготовления.
     */
    public void setSteps(String steps) {
        this.steps = steps;
    }

    /**
     * Возвращает рейтинг рецепта.
     *
     * @return рейтинг рецепта.
     */
    public float getRating() {
        return rating;
    }

    /**
     * Устанавливает рейтинг рецепта.
     *
     * @param rating рейтинг рецепта.
     */
    public void setRating(float rating) {
        this.rating = rating;
    }
}
