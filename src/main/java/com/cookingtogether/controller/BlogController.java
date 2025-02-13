package com.cookingtogether.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cookingtogether.Blog;
import com.cookingtogether.Comment;
import com.cookingtogether.Recipe;
import com.cookingtogether.User;
import com.cookingtogether.repository.BlogRepo;
import com.cookingtogether.repository.CommentRepo;
import com.cookingtogether.repository.RecipeRepo;
import com.cookingtogether.service.BlogService;
import com.cookingtogether.service.CommentService;
import com.cookingtogether.service.UserService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления сущностями {@link Blog}.
 * <p>
 * Этот контроллер предоставляет API для создания, обновления, получения и удаления блогов.
 * </p>
 * 
 * @see Blog
 */
@Controller
@RequestMapping(value = "/blogs", produces = "text/html; charset=UTF-8")
public class BlogController {

    @Autowired
    private BlogRepo blogRepo;
    
    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;
    
    @Autowired
    private CommentRepo commentRepo;
    
    @Autowired
    private RecipeRepo recipeRepository;
    
    @Autowired
    private UserService userService;

    /**
     * Получить список всех блогов.
     * 
     * @param model Модель для передачи данных на страницу.
     * @return Имя HTML-шаблона для отображения списка блогов.
     */
    @GetMapping
    public String getAllBlogs(Model model) {
        List<Blog> blogs = blogRepo.findAll();
        model.addAttribute("blogs", blogs);
        return "blog"; // Имя HTML-файла для отображения списка блогов
    }

    /**
     * Получить все блоги с дополнительной информацией.
     * 
     * @param model Модель для передачи данных на страницу.
     * @return Имя HTML-шаблона для отображения блогов.
     */
    @GetMapping("/view")
    public String viewBlogs(Model model) {
        List<Blog> blogs = blogRepo.findAll();
        System.out.println("Загруженные блоги: " + blogs);
        model.addAttribute("blogs", blogs);
        return "blog"; // Имя HTML-шаблона для отображения блогов
    }

    /**
     * Отображает форму для создания нового блога.
     * 
     * @param model Модель для передачи данных на страницу.
     * @param user Текущий авторизованный пользователь.
     * @return Имя HTML-шаблона для формы создания блога.
     */
    @GetMapping("/create")
    public String showCreateBlogForm(Model model, @AuthenticationPrincipal User user) {
        List<Recipe> recipes = recipeRepository.findByUserId(user.getId());
        if (recipes == null) {
            recipes = Collections.emptyList();
        }
        model.addAttribute("recipes", recipes);
        return "blog-form"; // Шаблон формы для создания блога
    }

    /**
     * Создает новый блог и сохраняет его в базе данных.
     * 
     * @param title Название блога.
     * @param description Описание блога.
     * @param recipeId Идентификатор рецепта, связанного с блогом.
     * @param user Текущий авторизованный пользователь.
     * @return Редирект на страницу списка блогов.
     */
    @PostMapping("/create")
    public String createBlog(
        @RequestParam(name = "title") String title,
        @RequestParam(name = "description") String description,
        @RequestParam(name = "recipeId") Integer recipeId,
        @AuthenticationPrincipal User user) {

        if (title == null || title.isBlank() || description == null || description.isBlank()) {
            throw new IllegalArgumentException("Название и описание не могут быть пустыми");
        }
        
        if (recipeId == null) {
            throw new IllegalArgumentException("Выберите рецепт");
        }

        Recipe recipe = recipeRepository.findById(recipeId)
            .orElseThrow(() -> new IllegalArgumentException("Рецепт не найден"));

        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setDescription(description);
        blog.setUserId(user.getId()); // Устанавливаем ID пользователя
        blog.setRecipe(recipe);
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUpdatedAt(LocalDateTime.now());

        blogRepo.save(blog);
        return "redirect:/blogs"; // Перенаправление на страницу списка блогов
    }

    /**
     * Получить блог по его уникальному идентификатору (slug).
     * 
     * @param slug Уникальный идентификатор блога.
     * @param model Модель для передачи данных на страницу.
     * @return Имя HTML-шаблона с деталями блога.
     */
    @GetMapping("/{slug}")
    public String getBlogBySlug(@PathVariable("slug") String slug, Model model) {
        Optional<Blog> blog = blogService.getBlogBySlug(slug);
        if (blog.isPresent()) {
            Blog blogData = blog.get();
            
            model.addAttribute("comment", new Comment());
            
            // Форматируем дату перед добавлением в модель
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String formattedCreatedAt = blogData.getCreatedAt().format(formatter);
            String formattedUpdatedAt = blogData.getUpdatedAt().format(formatter);
            
            model.addAttribute("blog", blogData);
            model.addAttribute("recipe", blogData.getRecipe());
            model.addAttribute("createdAt", formattedCreatedAt);
            model.addAttribute("updatedAt", formattedUpdatedAt);
            
            List<Comment> comments = commentService.getCommentsByRecipe(blogData.getRecipe().getId());
            model.addAttribute("comments", comments);
            
            return "blog-details"; // Шаблон для отображения деталей блога
        } else {
            return "redirect:/blogs"; // Перенаправление, если блог не найден
        }
    }

    /**
     * Добавляет комментарий к блогу.
     * 
     * @param slug Уникальный идентификатор блога.
     * @param comment Комментарий, который будет добавлен.
     * @param model Модель для передачи данных на страницу.
     * @param user Текущий авторизованный пользователь.
     * @return Перенаправление обратно на страницу блога.
     */
    @PostMapping("/{slug}/comment")
    public String addComment(@PathVariable("slug") String slug, @ModelAttribute Comment comment, Model model,
                             @AuthenticationPrincipal User user) {
        Optional<Blog> blog = blogService.getBlogBySlug(slug);
        if (blog.isPresent()) {
            Blog blogData = blog.get();
            
            // Привязываем комментарий к рецепту и пользователю
            comment.setRecipe(blogData.getRecipe());
            comment.setUser(user); // Устанавливаем текущего пользователя
            
            // Сохраняем комментарий
            commentService.addComment(comment);
            
            // Перенаправляем обратно на страницу блога
            return "redirect:/blogs/" + slug;
        } else {
            return "redirect:/blogs"; // Перенаправление, если блог не найден
        }
    }

    /**
     * Страница управления блогами пользователя.
     * 
     * @param model Модель для передачи данных на страницу.
     * @param user Текущий авторизованный пользователь.
     * @return Имя HTML-шаблона для управления блогами.
     */
    @GetMapping("/manage")
    public String manageBlogs(Model model, @AuthenticationPrincipal User user) {
        // Получаем блоги пользователя
        List<Blog> blogs = blogRepo.findByUserId(user.getId());
        model.addAttribute("blogs", blogs);
        return "manage-blogs"; // Шаблон для страницы управления блогами
    }

    /**
     * Обновить существующий блог.
     * 
     * @param id Идентификатор блога для обновления.
     * @param blog Обновленный объект блога.
     * @return Ответ с обновленным блогом или ошибка 404, если блог не найден.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable int id, @RequestBody Blog blog) {
        return blogRepo.findById(id)
                .map(existingBlog -> {
                    existingBlog.setTitle(blog.getTitle());
                    existingBlog.setDescription(blog.getDescription());
                    existingBlog.setUpdatedAt(blog.getUpdatedAt());
                    return ResponseEntity.ok(blogRepo.save(existingBlog));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Удалить блог по ID.
     * 
     * @param id Идентификатор блога для удаления.
     * @param user Текущий авторизованный пользователь.
     * @return Перенаправление на страницу управления блогами после удаления.
     */
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteBlog(@PathVariable("id") int id, @AuthenticationPrincipal User user) {
        Optional<Blog> blogOpt = blogRepo.findById(id);
        if (blogOpt.isPresent()) {
            Blog blog = blogOpt.get();
            if (blog.getUserId().equals(user.getId())) {
                // Удаляем все комментарии, связанные с этим блогом
                commentRepo.deleteByRecipeId(blog.getId());
                // Теперь можно удалить сам блог
                blogRepo.delete(blog);
            }
        }
        return "redirect:/blogs/manage"; // Перенаправление на страницу управления блогами
    }
}
