package com.cookingtogether.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cookingtogether.Blog;

import java.util.List;

/**
 * Интерфейс BlogRepo предоставляет методы для взаимодействия с сущностью {@link Blog}.
 * <p>
 * Этот интерфейс наследуется от {@link JpaRepository}, предоставляя базовые операции CRUD 
 * и дополнительные возможности работы с базой данных.
 * </p>
 * <p>
 * Пример использования:
 * <pre>
 * {@code
 * @Autowired
 * private BlogRepo blogRepo;
 * 
 * Blog newBlog = new Blog(1, 1, "Название", "Описание", new Date(), new Date());
 * blogRepo.save(newBlog);
 * 
 * List<Blog> blogs = blogRepo.findAll();
 * }
 * </pre>
 * </p>
 * 
 * @see Blog
 * @see JpaRepository
 */
@Repository
public interface BlogRepo extends JpaRepository<Blog, Integer> {

    /**
     * Метод для поиска всех блогов пользователя по его идентификатору.
     *
     * @param long1 идентификатор пользователя.
     * @return список блогов, принадлежащих указанному пользователю.
     */
    List<Blog> findByUserId(Long long1);

    /**
     * Метод для поиска блога по его названию.
     *
     * @param title название блога.
     * @return объект {@link Blog}, соответствующий указанному названию.
     */
    Blog findByTitle(String title);
    
    /**
     * Метод для поиска блога по его уникальному slug.
     *
     * @param slug уникальный идентификатор блога в виде строки.
     * @return объект {@link Blog}, соответствующий указанному slug.
     */
    Blog findBySlug(String slug);
}
