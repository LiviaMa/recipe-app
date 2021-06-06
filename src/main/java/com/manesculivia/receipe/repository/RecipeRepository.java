package com.manesculivia.receipe.repository;

import com.manesculivia.receipe.model.Recipe;
import com.manesculivia.receipe.model.RecipeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findAllByType(RecipeType recipeType);

}
