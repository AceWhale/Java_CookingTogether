package com.cookingtogether;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.sql.Date;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import jakarta.persistence.*;

/**
 * Класс, представляющий блог, содержащий информацию о рецептах и их описаниях.
 * Этот класс используется для хранения информации о блоге в базе данных.
 */
@Entity
@Table(name = "blogs")
public class Blog {

    /**
     * Идентификатор блога.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    /**
     * Идентификатор пользователя, который создал блог.
     */
    private Long userId;
    
    /**
     * Заголовок блога.
     */
    private String title;
    
    /**
     * Описание блога.
     */
    private String description;
    
    /**
     * Дата создания блога.
     */
    private LocalDateTime createdAt;
    
    /**
     * Дата последнего обновления блога.
     */
    private LocalDateTime updatedAt;
    
    @Column(unique = true, nullable = false)
    private String slug;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    
    public Blog() {
    	this.id = 0;
        this.userId = null;
        this.title = "";
        this.description = "";
        this.slug = "";
        this.createdAt = null;
        this.updatedAt = null;
    }

    /**
     * Конструктор для создания нового объекта блога с заданными параметрами.
     * 
     * @param id Идентификатор блога.
     * @param userId Идентификатор пользователя.
     * @param title Заголовок блога.
     * @param description Описание блога.
     * @param createdAt Дата создания блога.
     * @param updatedAt Дата последнего обновления блога.
     */
    public Blog(int id, long userId, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.slug = generateSlug(title);
    }
    
    private String generateSlug(String title) {
        return StringUtils.stripAccents(title)
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-");
    }
    
    @PrePersist
    @PreUpdate
    public void preSave() {
        this.slug = generateSlug(this.title);
    }

    // Геттеры и сеттеры
    public String getSlug() {
        return slug;
    }

    /**
     * Получить идентификатор блога.
     * 
     * @return Идентификатор блога.
     */
    public int getId() {
        return id;
    }

    /**
     * Установить идентификатор блога.
     * 
     * @param id Идентификатор блога.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Получить идентификатор пользователя, создавшего блог.
     * 
     * @return Идентификатор пользователя.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Установить идентификатор пользователя, создавшего блог.
     * 
     * @param long1 Идентификатор пользователя.
     */
    public void setUserId(Long long1) {
        this.userId = long1;
    }

    /**
     * Получить заголовок блога.
     * 
     * @return Заголовок блога.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Установить заголовок блога.
     * 
     * @param title Заголовок блога.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Получить описание блога.
     * 
     * @return Описание блога.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Установить описание блога.
     * 
     * @param description Описание блога.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Получить дату создания блога.
     * 
     * @return Дата создания блога.
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Установить дату создания блога.
     * 
     * @param localDateTime Дата создания блога.
     */
    public void setCreatedAt(LocalDateTime localDateTime) {
        this.createdAt = localDateTime;
    }

    /**
     * Получить дату последнего обновления блога.
     * 
     * @return Дата последнего обновления блога.
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Установить дату последнего обновления блога.
     * 
     * @param localDateTime Дата последнего обновления блога.
     */
    public void setUpdatedAt(LocalDateTime localDateTime) {
        this.updatedAt = localDateTime;
    }
    
 // Геттер для Thymeleaf
    public Recipe getRecipe() {
        return recipe;
    }

    // Сеттер
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
