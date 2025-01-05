package com.cookingtogether.controller;

import com.cookingtogether.hibernate.Recipe;
import com.cookingtogether.repository.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RecipeController {

    private final RecipeRepo recipeRepository;

    @Autowired
    public RecipeController(RecipeRepo recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // Главная страница с рецептами
    @GetMapping("/")
    public String getRecipes(Model model) {
        List<Recipe> recipes = recipeRepository.findAll(); // Получаем все рецепты из базы
        System.out.println("Рецепты: " + recipes); // Печать списка в консоль
        model.addAttribute("recipes", recipes);
        return "index";
    }


    // Страница с подробной информацией о рецепте
    @GetMapping("/recipe/{id}")
    public String recipeDetails(@PathVariable int id, Model model) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        model.addAttribute("recipe", recipe);
        return "recipe_details";  // Шаблон для подробной информации
    }

    // Страница для создания нового рецепта
    @GetMapping("/create")
    public String createRecipeForm() {
        return "create_recipe";  // Шаблон для создания рецепта (например, форма)
    }

    // Обработчик для создания рецепта
    @PostMapping("/create")
    public String createRecipe(@RequestParam int blogId,
                               @RequestParam String title,
                               @RequestParam List<String> ingredients,
                               @RequestParam String steps,
                               @RequestParam float rating) {
        // Создаем новый рецепт, используя конструктор
        Recipe newRecipe = new Recipe(0, blogId, title, ingredients, steps, rating);

        // Сохраняем рецепт в базе данных
        recipeRepository.save(newRecipe);

        return "redirect:/";  // Перенаправляем на главную страницу с рецептами
    }
}

