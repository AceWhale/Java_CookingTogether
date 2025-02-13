package com.cookingtogether;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.apache.commons.lang3.StringUtils;

/**
 * Представляет блог, содержащий информацию о рецептах и их описаниях.
 * Этот класс используется для хранения данных о блоге в базе данных.
 */
@Entity
@Table(name = "blogs")
public class Blog {

    /**
     * Уникальный идентификатор блога.
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

    /**
     * Уникальный URL-идентификатор блога (slug).
     */
    @Column(unique = true, nullable = false)
    private String slug;

    /**
     * Связанный рецепт блога.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_id", referencedColumnName = "id")
    private Recipe recipe;

    /**
     * Конструктор без параметров, инициализирует пустой объект блога.
     */
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
     * Создает блог с указанными параметрами.
     *
     * @param id          идентификатор блога
     * @param userId      идентификатор пользователя
     * @param title       заголовок блога
     * @param description описание блога
     * @param createdAt   дата создания блога
     * @param updatedAt   дата последнего обновления
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

    /**
     * Генерирует slug из заголовка блога.
     *
     * @param title заголовок блога
     * @return slug для URL
     */
    private String generateSlug(String title) {
        return StringUtils.stripAccents(title)
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s]", "")
                .replaceAll("\\s+", "-");
    }

    /**
     * Автоматически обновляет slug перед сохранением или обновлением записи.
     */
    @PrePersist
    @PreUpdate
    public void preSave() {
        this.slug = generateSlug(this.title);
    }

    /**
     * Получает slug блога.
     *
     * @return slug
     */
    public String getSlug() {
        return slug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
