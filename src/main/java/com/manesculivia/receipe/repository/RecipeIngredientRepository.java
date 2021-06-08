package com.manesculivia.receipe.repository;

import com.manesculivia.receipe.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {

    Optional<RecipeIngredient> findByNameIgnoreCaseAndQuantity(String name, Integer quantity);

}
