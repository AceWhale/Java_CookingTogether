package com.cookingtogether.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookingtogether.Recipe;
import com.cookingtogether.repository.RecipeRepo;

import java.util.List;

/**
 * Сервис для работы с рецептами.
 * <p>
 * Этот сервис использует {@link RecipeRepo} для выполнения операций с рецептами,
 * включая получение, создание, обновление и удаление рецептов.
 * </p>
 */
@Service
public class RecipeService {

    @Autowired
    private RecipeRepo recipeRepo;

    /**
     * Получить все рецепты.
     *
     * @return список всех рецептов.
     */
    public List<Recipe> getAllRecipes() {
        return recipeRepo.findAll();
    }

    /**
     * Найти рецепт по ID.
     *
     * @param id идентификатор рецепта.
     * @return рецепт с заданным ID, если он существует, иначе null.
     */
    public Recipe getRecipeById(int id) {
        return recipeRepo.findById(id).orElse(null);
    }

    /**
     * Найти рецепты по заголовку.
     *
     * @param title заголовок рецепта.
     * @return список рецептов с заданным заголовком.
     */
    public List<Recipe> getRecipesByTitle(String title) {
        return recipeRepo.findByTitle(title);
    }

    /**
     * Найти рецепты, созданные пользователем.
     *
     * @param userId идентификатор пользователя.
     * @return список рецептов, созданных указанным пользователем.
     */
    public List<Recipe> getRecipesByUserId(Long userId) {
        return recipeRepo.findByUserId(userId);
    }

    /**
     * Сохранить новый рецепт.
     *
     * @param recipe новый рецепт для сохранения.
     * @return сохраненный рецепт.
     */
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepo.save(recipe);
    }

    /**
     * Удалить рецепт по ID.
     *
     * @param id идентификатор рецепта для удаления.
     */
    public void deleteRecipe(int id) {
        recipeRepo.deleteById(id);
    }
}
