package com.cookingtogether.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cookingtogether.Blog;
import com.cookingtogether.repository.BlogRepo;

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
@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    @Autowired
    private BlogRepo blogRepo;

    /**
     * Получить список всех блогов.
     *
     * @return список блогов.
     */
    @GetMapping
    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }

    /**
     * Получить блог по ID.
     *
     * @param id идентификатор блога.
     * @return объект {@link Blog} или статус 404, если блог не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable int id) {
        Optional<Blog> blog = blogRepo.findById(id);
        return blog.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

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
