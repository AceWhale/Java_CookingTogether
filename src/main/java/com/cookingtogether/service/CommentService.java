package com.cookingtogether.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookingtogether.Comment;
import com.cookingtogether.repository.CommentRepo;

import java.util.List;

/**
 * Сервис для управления комментариями.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepository;

    /**
     * Получить список комментариев для определённого рецепта.
     *
     * @param recipeId идентификатор рецепта.
     * @return список комментариев, связанных с данным рецептом.
     */
    public List<Comment> getCommentsByRecipe(int recipeId) {
        return commentRepository.findByRecipeId(recipeId);
    }

    /**
     * Получить список комментариев, созданных определённым пользователем.
     *
     * @param userId идентификатор пользователя.
     * @return список комментариев, созданных данным пользователем.
     */
    public List<Comment> getCommentsByUser(int userId) {
        return commentRepository.findByUserId(userId);
    }

    /**
     * Добавить новый комментарий.
     *
     * @param comment объект комментария.
     * @return добавленный комментарий.
     */
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * Удалить комментарий по идентификатору.
     *
     * @param commentId идентификатор комментария.
     */
    public void deleteComment(int commentId) {
        commentRepository.deleteById(commentId);
    }
}
