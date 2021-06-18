package com.manesculivia.recipe.service;

import com.manesculivia.recipe.model.RecipeIngredient;
import com.manesculivia.recipe.model.request.IngredientRequestDto;
import com.manesculivia.recipe.repository.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository;

    public RecipeIngredient findOrInsert(IngredientRequestDto ingredient) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setName(ingredient.getName());
        recipeIngredient.setQuantity(ingredient.getQuantity());

        return recipeIngredientRepository.findByNameIgnoreCaseAndQuantity(ingredient.getName(), ingredient.getQuantity())
                .orElseGet(() -> recipeIngredientRepository.save(recipeIngredient));
    }

}
