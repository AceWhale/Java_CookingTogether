package com.cookingtogether;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.sql.Date;

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
    private int userId;
    
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
    private Date createdAt;
    
    /**
     * Дата последнего обновления блога.
     */
    private Date updatedAt;

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
    public Blog(int id, int userId, String title, String description, Date createdAt, Date updatedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
    public int getUserId() {
        return userId;
    }

    /**
     * Установить идентификатор пользователя, создавшего блог.
     * 
     * @param userId Идентификатор пользователя.
     */
    public void setUserId(int userId) {
        this.userId = userId;
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
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Установить дату создания блога.
     * 
     * @param createdAt Дата создания блога.
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Получить дату последнего обновления блога.
     * 
     * @return Дата последнего обновления блога.
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Установить дату последнего обновления блога.
     * 
     * @param updatedAt Дата последнего обновления блога.
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
