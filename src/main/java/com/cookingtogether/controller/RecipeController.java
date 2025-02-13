package com.cookingtogether.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import com.cookingtogether.User;
import com.cookingtogether.repository.RecipeRepo;

import java.util.Arrays;
import java.util.List;

/**
 * Контроллер для управления рецептами.
 * <p>
 * Этот контроллер отвечает за отображение рецептов, создание новых рецептов и отображение подробной информации о рецепте.
 * </p>
 */
@Controller
public class RecipeController {

    private final RecipeRepo recipeRepository;

    /**
     * Конструктор для инициализации {@link RecipeController} с зависимостью от {@link RecipeRepo}.
     *
     * @param recipeRepository Репозиторий для работы с рецептами.
     */
    public RecipeController(RecipeRepo recipeRepository) {
        this.recipeRepository = recipeRepository;
    }
    
    /**
     * Тестовый метод для получения всех рецептов.
     * 
     * @return список всех рецептов.
     */
    @GetMapping("/recipes/test")
    @ResponseBody
    public List<Recipe> testRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * Отображает главную страницу с рецептом.
     * 
     * @param model модель для передачи данных в представление.
     * @return имя шаблона для главной страницы с рецептами.
     */
    @GetMapping("/")
    public String getRecipes(Model model) {
        List<Recipe> recipes = recipeRepository.findAll(); // Получаем все рецепты из базы
        System.out.println("Рецепты: " + recipes); // Печать списка в консоль
        model.addAttribute("recipes", recipes);
        return "index";
    }
    
    /**
     * Получает рецепты пользователя по его ID.
     * 
     * @param userId идентификатор пользователя.
     * @return список рецептов пользователя или 404, если рецепты не найдены.
     */
    @GetMapping("/recipe/user/{userId}")
    public ResponseEntity<List<Recipe>> getRecipeByUserId(@PathVariable Long userId) {
        List<Recipe> recipes = recipeRepository.findByUserId(userId);
        if (recipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }

    /**
     * Отображает подробную информацию о рецепте.
     * 
     * @param id идентификатор рецепта.
     * @param model модель для передачи данных в представление.
     * @return имя шаблона для отображения подробностей рецепта.
     */
    @GetMapping("/recipe/{id}")
    public String recipeDetails(@PathVariable int id, Model model) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        model.addAttribute("recipe", recipe);
        return "recipe_details";  // Шаблон для подробной информации
    }

    /**
     * Отображает страницу для создания нового рецепта.
     * 
     * @return имя шаблона для создания рецепта.
     */
    @GetMapping("/create")
    public String createRecipeForm() {
        return "create_recipe";  // Шаблон для создания рецепта (например, форма)
    }

    /**
     * Обрабатывает создание нового рецепта.
     * 
     * @param title название рецепта.
     * @param blogId идентификатор блога.
     * @param ingredients ингредиенты рецепта, разделённые запятыми.
     * @param steps шаги приготовления рецепта.
     * @param rating рейтинг рецепта.
     * @param imageFile изображение рецепта (необязательно).
     * @param user текущий аутентифицированный пользователь.
     * @return редирект на главную страницу или страницу с ошибкой, если изображение не удалось загрузить.
     */
    @PostMapping("/create")
    public String createRecipe(
            @RequestParam("title") String title,
            @RequestParam("blogId") int blogId,
            @RequestParam("ingredients") String ingredients,
            @RequestParam("steps") String steps,
            @RequestParam("rating") double rating,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @AuthenticationPrincipal User user) {
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

        // Устанавливаем пользователя для рецепта
        newRecipe.setUser(user);

        // Сохраняем рецепт в базе данных
        recipeRepository.save(newRecipe);

        return "redirect:/";
    }
}
