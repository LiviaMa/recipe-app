package com.manesculivia.receipe.service;

import com.manesculivia.receipe.model.RecipeIngredient;
import com.manesculivia.receipe.model.request.IngredientRequestDto;
import com.manesculivia.receipe.repository.RecipeIngredientRepository;
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
                .orElse(recipeIngredientRepository.save(recipeIngredient));
    }

}
