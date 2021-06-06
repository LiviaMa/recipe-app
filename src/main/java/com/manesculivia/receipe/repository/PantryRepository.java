package com.manesculivia.receipe.repository;

import com.manesculivia.receipe.model.PantryIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PantryRepository extends JpaRepository<PantryIngredient, Integer> {

    Optional<PantryIngredient> findByIngredientNameIgnoreCaseAndIngredientQuantityLessThanEqual(String name, Integer quantity);

}
