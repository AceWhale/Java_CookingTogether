package com.cookingtogether.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cookingtogether.Comment;
import com.cookingtogether.service.CommentService;

import java.util.List;

/**
 * Контроллер для управления комментариями.
 * <p>
 * Обеспечивает API для добавления, удаления и просмотра комментариев.
 * </p>
 * 
 * @see CommentService
 */
@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * Получить список комментариев для определённого рецепта.
     *
     * @param recipeId идентификатор рецепта.
     * @return список комментариев, связанных с данным рецептом.
     */
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<Comment>> getCommentsByRecipe(@PathVariable int recipeId) {
        List<Comment> comments = commentService.getCommentsByRecipe(recipeId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Получить список комментариев, созданных пользователем.
     *
     * @param userId идентификатор пользователя.
     * @return список комментариев, созданных данным пользователем.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable int userId) {
        List<Comment> comments = commentService.getCommentsByUser(userId);
        return ResponseEntity.ok(comments);
    }

    /**
     * Добавить новый комментарий.
     *
     * @param comment объект комментария, который нужно добавить.
     * @return добавленный комментарий.
     */
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody Comment comment) {
        Comment savedComment = commentService.addComment(comment);
        return ResponseEntity.ok(savedComment);
    }

    /**
     * Удалить комментарий по идентификатору.
     *
     * @param commentId идентификатор комментария.
     * @return ответ с подтверждением удаления.
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
