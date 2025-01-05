package com.cookingtogether.service;

import com.cookingtogether.hibernate.Rating;
import com.cookingtogether.repository.RatingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepo ratingRepo;

    public List<Rating> getAllRatings() {
        return ratingRepo.findAll();
    }

    public Optional<Rating> getRatingById(int id) {
        return ratingRepo.findById(id);
    }

    public Rating createRating(Rating rating) {
        return ratingRepo.save(rating);
    }
    
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


    public boolean deleteRating(int id) {
        if (ratingRepo.existsById(id)) {
            ratingRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
