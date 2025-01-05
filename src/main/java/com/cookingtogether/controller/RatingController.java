package com.cookingtogether.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.cookingtogether.Rating;
import com.cookingtogether.service.RatingService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import java.util.List;

/**
 * Контроллер для управления рейтингами рецептов.
 * Предоставляет API для получения, сохранения, обновления, удаления рейтингов и оценки рецепта.
 */
@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    /**
     * Получить рейтинг по ID или все рейтинги.
     *
     * @param id идентификатор рейтинга (необязательный).
     * @return список всех рейтингов или один рейтинг.
     */
    @GetMapping
    public ResponseEntity<Object> getRatingOrAll(@RequestParam(name = "id", required = false) Integer id) {
        if (id != null) {
            Rating rating = ratingService.getRatingById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "RATING ID " + id + " NOT FOUND"));
            return ResponseEntity.ok(rating);
        }
        List<Rating> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    /**
     * Добавить новый рейтинг.
     *
     * @param rating объект рейтинга.
     * @return сообщение об успешном добавлении рейтинга.
     */
    @PostMapping
    public ResponseEntity<String> addRating(@RequestBody Rating rating) {
        try {
            ratingService.createRating(rating);
            return ResponseEntity.status(HttpStatus.CREATED).body("RATING CREATED");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR - FAILED TO CREATE RATING: " + e.getMessage());
        }
    }

    /**
     * Обновить существующий рейтинг.
     *
     * @param id     идентификатор рейтинга.
     * @param rating объект рейтинга с обновленными данными.
     * @return обновленный рейтинг.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRating(@PathVariable("id") Integer id, @RequestBody Rating rating) {
        Rating updatedRating = ratingService.updateRating(id, rating);
        if (updatedRating == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "RATING ID " + id + " NOT FOUND");
        }
        return ResponseEntity.ok(updatedRating);
    }


    /**
     * Удалить рейтинг по ID.
     *
     * @param id идентификатор рейтинга.
     * @return сообщение об успешном удалении или причина ошибки.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable("id") Integer id) {
        try {
            if (ratingService.deleteRating(id)) {
                return ResponseEntity.ok("RATING DELETED");
            } else {
                throw new EntityNotFoundException("RATING ID " + id + " NOT FOUND");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("RATING ID " + id + " NOT FOUND");
        } catch (OptimisticLockException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CONFLICT ERROR - RATING ID " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR - FAILED TO DELETE RATING: " + e.getMessage());
        }
    }

    /**
     * Оценить рецепт (метод из диаграммы).
     *
     * @param recipeId идентификатор рецепта.
     * @param rating   рейтинг, который будет выставлен.
     * @return сообщение об успешной оценке.
     */
    @PostMapping("/rate/{recipeId}")
    public ResponseEntity<String> rateRecipe(@PathVariable("recipeId") Integer recipeId, @RequestBody Rating rating) {
        try {
            ratingService.rateRecipe(recipeId, rating);
            return ResponseEntity.ok("RECIPE RATED SUCCESSFULLY");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("ERROR - FAILED TO RATE RECIPE: " + e.getMessage());
        }
    }
}
