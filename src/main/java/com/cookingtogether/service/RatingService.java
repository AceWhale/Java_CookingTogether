package com.cookingtogether.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookingtogether.Rating;
import com.cookingtogether.repository.RatingRepo;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления рейтингами рецептов.
 * <p>
 * Этот сервис содержит бизнес-логику для работы с рейтингами рецептов, включая создание, обновление,
 * удаление и получение информации о рейтингах.
 * </p>
 */
@Service
public class RatingService {

    @Autowired
    private RatingRepo ratingRepo;

    /**
     * Получить все рейтинги.
     *
     * @return список всех рейтингов.
     */
    public List<Rating> getAllRatings() {
        return ratingRepo.findAll();
    }

    /**
     * Получить рейтинг по ID.
     *
     * @param id идентификатор рейтинга.
     * @return объект рейтинга, если найден, иначе Optional.empty().
     */
    public Optional<Rating> getRatingById(int id) {
        return ratingRepo.findById(id);
    }

    /**
     * Создать новый рейтинг.
     *
     * @param rating объект рейтинга для сохранения.
     * @return созданный рейтинг.
     */
    public Rating createRating(Rating rating) {
        return ratingRepo.save(rating);
    }

    /**
     * Оценить рецепт.
     * <p>
     * Если рейтинг для данного рецепта и пользователя уже существует, то он будет обновлён.
     * Если рейтинга нет, создаётся новый.
     * </p>
     *
     * @param recipeId идентификатор рецепта, который оценивается.
     * @param rating   объект рейтинга с оценкой.
     */
    public void rateRecipe(int recipeId, Rating rating) {
        // Найти существующий рейтинг для данного рецепта и пользователя
        Optional<Rating> existingRating = ratingRepo.findByRecipeIdAndUserId(recipeId, rating.getUserId());

        if (existingRating.isPresent()) {
            // Если рейтинг существует, обновить его
            Rating ratingToUpdate = existingRating.get();
            ratingToUpdate.setScore(rating.getScore()); // Обновляем рейтинг
            ratingRepo.save(ratingToUpdate);
        } else {
            // Если рейтинга нет, создать новый
            rating.setRecipeId(recipeId); // Устанавливаем идентификатор рецепта
            ratingRepo.save(rating); // Сохраняем новый рейтинг
        }
    }

    /**
     * Обновить существующий рейтинг.
     *
     * @param id     идентификатор рейтинга для обновления.
     * @param rating объект рейтинга с обновлёнными данными.
     * @return обновлённый рейтинг, если найден, иначе null.
     */
    public Rating updateRating(int id, Rating rating) {
        Optional<Rating> existingRatingOpt = ratingRepo.findById(id);

        if (existingRatingOpt.isPresent()) {
            Rating existingRating = existingRatingOpt.get();
            existingRating.setRecipeId(rating.getRecipeId());
            existingRating.setUserId(rating.getUserId());
            existingRating.setScore(rating.getScore());
            return ratingRepo.save(existingRating);
        } else {
            return null;  // Если рейтинг с таким id не найден
        }
    }

    /**
     * Удалить рейтинг по ID.
     *
     * @param id идентификатор рейтинга для удаления.
     * @return true, если удаление прошло успешно, иначе false.
     */
    public boolean deleteRating(int id) {
        if (ratingRepo.existsById(id)) {
            ratingRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
