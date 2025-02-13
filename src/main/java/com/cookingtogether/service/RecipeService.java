package com.cookingtogether.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookingtogether.Recipe;
import com.cookingtogether.repository.RecipeRepo;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с рецептами.
 * Использует репозиторий RecipeRepo для взаимодействия с базой данных.
 */
@Service
public class RecipeService {

    @Autowired
    private RecipeRepo recipeRepo;

    /**
     * Найти все рецепты.
     *
     * @return Список всех рецептов.
     */
    public List<Recipe> getAllRecipes() {
        return recipeRepo.findAll();
    }

    /**
     * Найти рецепт по ID.
     *
     * @param id Идентификатор рецепта.
     * @return Рецепт с заданным ID.
     */
    public Recipe getRecipeById(int id) {
        return recipeRepo.findById(id).orElse(null);
    }

    /**
     * Найти рецепты по заголовку.
     *
     * @param title Заголовок рецепта.
     * @return Список рецептов с заданным заголовком.
     */
    public List<Recipe> getRecipesByTitle(String title) {
        return recipeRepo.findByTitle(title);
    }

    /**
     * Найти рецепты по категории.
     *
     * @param category Категория рецепта.
     * @return Список рецептов в заданной категории.
     */
    //public List<Recipe> getRecipesByCategory(String category) {
    //    return recipeRepo.findByCategory(category);
    //}

    /**
     * Найти рецепты, созданные пользователем.
     *
     * @param userId Идентификатор пользователя.
     * @return Список рецептов, созданных указанным пользователем.
     */
    public List<Recipe> getRecipesByUserId(Long userId) {
        return recipeRepo.findByUserId(userId);
    }

    /**
     * Сохранить новый рецепт.
     *
     * @param recipe Новый рецепт для сохранения.
     * @return Сохраненный рецепт.
     */
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepo.save(recipe);
    }

    /**
     * Удалить рецепт по ID.
     *
     * @param id Идентификатор рецепта для удаления.
     */
    public void deleteRecipe(int id) {
        recipeRepo.deleteById(id);
    }
}
