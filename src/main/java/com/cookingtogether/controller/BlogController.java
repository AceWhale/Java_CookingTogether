package com.cookingtogether.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cookingtogether.Blog;
import com.cookingtogether.Comment;
import com.cookingtogether.repository.BlogRepo;
import com.cookingtogether.service.BlogService;
import com.cookingtogether.service.CommentService;

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
    public Blog createBlog(@RequestBody Blog blog) {
        return blogRepo.save(blog);
    }
    
    @GetMapping("/{slug}")
    public String getBlogBySlug(@PathVariable("slug") String slug, Model model) {
        Optional<Blog> blog = blogService.getBlogBySlug(slug);
        if (blog.isPresent()) {
            model.addAttribute("blog", blog.get());
            model.addAttribute("recipe", blog.get().getRecipe()); // Добавляем рецепт
            
            List<Comment> comments = commentService.getCommentsByRecipe(blog.get().getRecipe().getId());
            model.addAttribute("comments", comments);
            
            return "blog-details"; // Название шаблона для страницы блога
        } else {
            return "redirect:/blogs"; // Перенаправляем на список блогов, если блог не найден
        }
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(@PathVariable int id) {
        if (blogRepo.existsById(id)) {
            blogRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
