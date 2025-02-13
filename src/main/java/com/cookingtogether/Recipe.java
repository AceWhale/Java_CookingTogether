package com.cookingtogether;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;

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
    
    /** Связь с пользователем, который создал рецепт. */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Идентификатор блога, к которому относится рецепт. */
    private int blogId;
    
    /** Имя файла изображения рецепта. */
    @Lob
    private String imagePath;

    /** Название рецепта. */
    private String title;

    /** Список ингредиентов рецепта. */
    @ElementCollection
    @Column(name = "ingredient")
    private List<String> ingredients;

    /** Шаги приготовления рецепта. */
    @Column(length = 1000)  // Ограничение по длине для длинных шагов
    private String steps;

    /** Рейтинг рецепта. */
    private float rating;
    
    /**
     * Конструктор по умолчанию, создающий пустой объект рецепта.
     */
    public Recipe() {
        this.id = 0;
        this.blogId = 0;
        this.title = "empty";
        this.ingredients = null;
        this.steps = "empty";
        this.rating = 0;
        this.imagePath = "";
    }

    /**
     * Конструктор для создания объекта рецепта с параметрами.
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

    /**
     * Получает уникальный идентификатор рецепта.
     *
     * @return идентификатор рецепта.
     */
    public int getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор рецепта.
     *
     * @param id идентификатор рецепта.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Получает пользователя, который создал рецепт.
     *
     * @return объект {@link User}, представляющий создателя рецепта.
     */
    public User getUser() {
        return user;
    }

    /**
     * Устанавливает пользователя, который создал рецепт.
     *
     * @param user объект {@link User}, представляющий создателя рецепта.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Получает идентификатор блога, к которому относится рецепт.
     *
     * @return идентификатор блога.
     */
    public int getBlogId() {
        return blogId;
    }

    /**
     * Устанавливает идентификатор блога, к которому относится рецепт.
     *
     * @param blogId идентификатор блога.
     */
    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    /**
     * Получает название рецепта.
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
     * Получает список ингредиентов рецепта.
     *
     * @return список ингредиентов.
     */
    public List<String> getIngredients() {
        return ingredients;
    }

    /**
     * Устанавливает список ингредиентов рецепта.
     *
     * @param ingredients список ингредиентов.
     */
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Получает шаги приготовления рецепта.
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
     * Получает рейтинг рецепта.
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
    
    /**
     * Получает путь к изображению рецепта.
     *
     * @return путь к изображению.
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Устанавливает путь к изображению рецепта.
     *
     * @param imagePath путь к изображению.
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
