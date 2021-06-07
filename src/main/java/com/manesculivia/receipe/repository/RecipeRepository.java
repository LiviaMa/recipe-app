package com.manesculivia.receipe.repository;

import com.manesculivia.receipe.model.Recipe;
import com.manesculivia.receipe.model.RecipeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findAllByType(RecipeType type);

    List<Recipe> findAllByCreatedByIgnoreCase(String createdBy);

    List<Recipe> findAllByTypeAndNameIgnoreCaseAndCookingTimeAndDifficulty(
            String name, Integer cookingTime, String difficulty);

    List<Recipe> findAllByTypeAndNameIgnoreCaseAndCookingTime(String name, Integer cookingTime);

    List<Recipe> findAllByTypeAndNameIgnoreCaseAndDifficulty(String name, String difficulty);

    List<Recipe> findAllByTypeAndCookingTimeAndDifficulty(Integer cookingTime, String difficulty);

    List<Recipe> findAllByTypeAndNameIgnoreCase(String name);

    List<Recipe> findAllByTypeAndCookingTime(Integer cookingTime);

    List<Recipe> findAllByTypeAndDifficulty(String difficulty);

    Optional<Recipe> findByIdAndTypeIgnoreCaseAndCreatedByIgnoreCase(Integer id, String type, String createdBy);

    void deleteByIdAndRecipeType(Integer id, String type);

}
