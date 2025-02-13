package com.cookingtogether.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookingtogether.Blog;
import com.cookingtogether.repository.BlogRepo;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления сущностями {@link Blog}.
 * <p>
 * Содержит бизнес-логику для работы с блогами, включая CRUD-операции.
 * </p>
 * 
 * @see Blog
 * @see BlogRepo
 */
@Service
public class BlogService {

    @Autowired
    private BlogRepo blogRepo;

    /**
     * Получить все блоги.
     *
     * @return список всех блогов.
     */
    public List<Blog> getAllBlogs() {
        return blogRepo.findAll();
    }

    /**
     * Найти блог по ID.
     *
     * @param id идентификатор блога.
     * @return объект {@link Blog}, если он существует, или пустой Optional.
     */
    public Optional<Blog> getBlogById(int id) {
        return blogRepo.findById(id);
    }

    /**
     * Создать новый блог.
     *
     * @param blog объект {@link Blog} для создания.
     * @return созданный объект блога.
     */
    public Blog createBlog(Blog blog) {
        return blogRepo.save(blog);
    }

    /**
     * Обновить существующий блог.
     *
     * @param id   идентификатор блога для обновления.
     * @param blog объект {@link Blog} с обновлёнными данными.
     * @return обновлённый блог, если он существует, или пустой Optional.
     */
    public Optional<Blog> updateBlog(int id, Blog blog) {
        return blogRepo.findById(id).map(existingBlog -> {
            existingBlog.setTitle(blog.getTitle());
            existingBlog.setDescription(blog.getDescription());
            existingBlog.setUpdatedAt(blog.getUpdatedAt());
            return blogRepo.save(existingBlog);
        });
    }

    /**
     * Получить блог по его слагу (URL-псевдоним).
     *
     * @param slug уникальный идентификатор блога в виде слага.
     * @return объект {@link Blog}, если найден, иначе пустой Optional.
     */
    public Optional<Blog> getBlogBySlug(String slug) {
        return Optional.ofNullable(blogRepo.findBySlug(slug));
    }

    /**
     * Удалить блог по ID.
     *
     * @param id идентификатор блога для удаления.
     * @return true, если удаление успешно, иначе false.
     */
    public boolean deleteBlog(int id) {
        if (blogRepo.existsById(id)) {
            blogRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
