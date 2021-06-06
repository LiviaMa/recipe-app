package com.manesculivia.receipe.service;

import com.manesculivia.receipe.model.RecipeIngredient;
import com.manesculivia.receipe.model.Recipe;
import com.manesculivia.receipe.model.RecipeType;
import com.manesculivia.receipe.model.request.RecipeRequestDto;
import com.manesculivia.receipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeIngredientService recipeIngredientService;

    public List<Recipe> getAllRecipes(RecipeType recipeType) {
        return recipeRepository.findAllByType(recipeType);
    }

    public Recipe createRecipe(RecipeRequestDto recipeRequestDto, RecipeType recipeType) {
        Recipe recipe = Recipe.builder()
                .name(recipeRequestDto.getName())
                .type(recipeType)
                .cookingTime(recipeRequestDto.getCookingTime())
                .difficulty(recipeRequestDto.getDifficulty())
                .recipeIngredients(getIngredients(recipeRequestDto))
                .build();
        return recipeRepository.save(recipe);
    }

    private List<RecipeIngredient> getIngredients(RecipeRequestDto recipeRequestDto) {
        return recipeRequestDto.getIngredients().stream()
                .map(recipeIngredientService::findOrInsert)
                .collect(toList());
    }

}
