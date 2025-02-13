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
     * @return список блогов.
     */
    @GetMapping
    public String getAllBlogs(Model model) {
        List<Blog> blogs = blogRepo.findAll();
        model.addAttribute("blogs", blogs);
        return "blog"; // Имя HTML-файла в папке templates
    }
    @GetMapping("/view")
    public String viewBlogs(Model model) {
        List<Blog> blogs = blogRepo.findAll();
        System.out.println("Загруженные блоги: " + blogs);
        model.addAttribute("blogs", blogs);
        return "blog";
    }
    
    @GetMapping("/create")
    public String showCreateBlogForm(Model model, @AuthenticationPrincipal User user) {
    	List<Recipe> recipes = recipeRepository.findByUserId(user.getId());
    	if (recipes == null) {
    	    recipes = Collections.emptyList();
    	}
    	model.addAttribute("recipes", recipes);
        model.addAttribute("recipes", recipes);
        return "blog-form";
    }
    
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
        return "redirect:/blogs";
    }


    /**
     * Получить блог по ID.
     *
     * @param id идентификатор блога.
     * @return объект {@link Blog} или статус 404, если блог не найден.
     */
    /*@GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable int id) {
        Optional<Blog> blog = blogRepo.findById(id);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    */

    /**
     * Создать новый блог.
     *
     * @param blog объект блога для создания.
     * @return созданный блог.
     */
    @PostMapping
    public Blog createNewBlog(@RequestBody Blog blog) {
        return blogRepo.save(blog);
    }
    
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
            
            return "blog-details";
        } else {
            return "redirect:/blogs";
        }
    }
    
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
            return "redirect:/blogs";
        }
    }
    
    @GetMapping("/manage")
    public String manageBlogs(Model model, @AuthenticationPrincipal User user) {
        // Получаем блоги пользователя
        List<Blog> blogs = blogRepo.findByUserId(user.getId());
        model.addAttribute("blogs", blogs);
        return "manage-blogs"; // Имя HTML-файла с блогами для управления
    }


    /**
     * Обновить существующий блог.
     *
     * @param id   идентификатор блога для обновления.
     * @param blog объект блога с обновлёнными данными.
     * @return обновлённый блог или статус 404, если блог не найден.
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
     * @param id идентификатор блога для удаления.
     * @return статус 204 (No Content) или 404, если блог не найден.
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
        return "redirect:/blogs/manage";
    }

    
}
