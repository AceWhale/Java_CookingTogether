package com.cookingtogether.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cookingtogether.Recipe;
import com.cookingtogether.repository.RecipeRepo;

import java.util.Arrays;
import java.util.List;

@Controller
public class RecipeController {

    private final RecipeRepo recipeRepository;

    public RecipeController(RecipeRepo recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    
    @GetMapping("/recipes/test")
    @ResponseBody
    public List<Recipe> testRecipes() {
        return recipeRepository.findAll();
    }

    // Главная страница с рецептами
    @GetMapping("/")
    public String getRecipes(Model model) {
        List<Recipe> recipes = recipeRepository.findAll(); // Получаем все рецепты из базы
        System.out.println("Рецепты: " + recipes); // Печать списка в консоль
        model.addAttribute("recipes", recipes);
        return "index";
    }
    
    @GetMapping("/recipe/user/{userId}")
    public ResponseEntity<Recipe> getRecipeByUserId(@PathVariable int userId) {
        return recipeRepository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
    public String createRecipe(
            @RequestParam("title") String title,
            @RequestParam("blogId") int blogId,
            @RequestParam("ingredients") String ingredients,
            @RequestParam("steps") String steps,
            @RequestParam("rating") double rating,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        String imagePath = null;

        // Получаем абсолютный путь к папке для загрузки файлов
        String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";

        java.io.File uploadFolder = new java.io.File(uploadDir);

        // Создаём папку, если её нет
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        // Сохраняем изображение на сервере
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                System.out.println("Загружено изображение: " + imageFile.getOriginalFilename());
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                imagePath = "uploads/" + fileName;

                // Сохраняем файл в указанную папку
                imageFile.transferTo(new java.io.File(uploadDir + fileName));
            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/create?error=upload_failed";
            }
        }

        // Преобразуем строку ингредиентов в список
        List<String> ingredientsList = Arrays.asList(ingredients.split("\\s*,\\s*"));

        // Создаем новый рецепт
        Recipe newRecipe = new Recipe(0, blogId, title, ingredientsList, steps, (float) rating);
        newRecipe.setImagePath(imagePath);

        // Сохраняем рецепт в базе данных
        recipeRepository.save(newRecipe);

        return "redirect:/";
    }
}

