package com.cookingtogether;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * Сущность, представляющая пользователя в системе.
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

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
    
    /**
     * Список рецептов, принадлежащих пользователю.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipe> recipes;

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
    public String getUsername() {
        return username;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param username имя пользователя.
     */
    public void setUsername(String username) {
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
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя.
     *
     * @param password пароль пользователя.
     */
    public void setPassword(String password) {
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
    
    /**
     * Возвращает список рецептов, принадлежащих пользователю.
     *
     * @return список рецептов.
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Устанавливает список рецептов для пользователя.
     *
     * @param recipes список рецептов.
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * Возвращает список прав (ролей) пользователя.
     * 
     * @return пустой список, так как роли не реализованы.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * Проверяет, не истекла ли учетная запись пользователя.
     *
     * @return всегда {@code true}, так как срок действия аккаунта не контролируется.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Проверяет, не заблокирован ли пользователь.
     *
     * @return всегда {@code true}, так как блокировка аккаунта не реализована.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Проверяет, не истекли ли учетные данные пользователя (пароль).
     *
     * @return всегда {@code true}, так как срок действия пароля не контролируется.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Проверяет, активен ли пользователь.
     *
     * @return всегда {@code true}, так как механизм деактивации пользователей не реализован.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
