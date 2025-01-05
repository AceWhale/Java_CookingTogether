package com.cookingtogether;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Сущность, представляющая пользователя в системе.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Версия сущности для контроля конкурентного доступа.
     */
    @Version
    private Long version;

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Электронная почта пользователя.
     */
    private String email;

    /**
     * Пароль пользователя.
     */
    private String password;

    /**
     * Роль пользователя (например, "USER").
     */
    private String role;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Recipe recipe;

    /**
     * Конструктор без параметров. По умолчанию устанавливает роль "USER".
     */
    public User() {
        this.role = "USER";
    }

    /**
     * Конструктор для создания пользователя с заданным именем, почтой и паролем.
     * По умолчанию устанавливает роль "USER".
     *
     * @param username имя пользователя.
     * @param email    электронная почта пользователя.
     * @param password пароль пользователя.
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = "USER";
    }

    /**
     * Возвращает уникальный идентификатор пользователя.
     *
     * @return уникальный идентификатор.
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор пользователя.
     *
     * @param id уникальный идентификатор.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return имя пользователя.
     */
    public String getName() {
        return username;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param username имя пользователя.
     */
    public void setName(String username) {
        this.username = username;
    }

    /**
     * Возвращает электронную почту пользователя.
     *
     * @return электронная почта.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает электронную почту пользователя.
     *
     * @param email электронная почта.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль пользователя.
     */
    public String getPass() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя.
     *
     * @param password пароль пользователя.
     */
    public void setPass(String password) {
        this.password = password;
    }

    /**
     * Возвращает роль пользователя.
     *
     * @return роль пользователя.
     */
    public String getRole() {
        return role;
    }
}
