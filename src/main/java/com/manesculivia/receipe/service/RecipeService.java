package com.manesculivia.receipe.service;

import com.manesculivia.receipe.exception.NotFoundException;
import com.manesculivia.receipe.model.Recipe;
import com.manesculivia.receipe.model.RecipeDifficulty;
import com.manesculivia.receipe.model.RecipeIngredient;
import com.manesculivia.receipe.model.RecipeType;
import com.manesculivia.receipe.model.request.RecipeRequestDto;
import com.manesculivia.receipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    private final RecipeIngredientService recipeIngredientService;

    public List<Recipe> getPublicRecipes(String name, Integer cookingTime, RecipeDifficulty difficulty) {
        return recipeRepository.findAllByTypeAndNameIgnoreCaseAndCookingTimeAndDifficulty(name, cookingTime, difficulty.name());
    }

    public List<Recipe> getPublicRecipes(String name, Integer cookingTime) {
        return recipeRepository.findAllByTypeAndNameIgnoreCaseAndCookingTime(name, cookingTime);
    }

    public List<Recipe> getPublicRecipes(String name, RecipeDifficulty difficulty) {
        return recipeRepository.findAllByTypeAndNameIgnoreCaseAndDifficulty(name, difficulty.name());
    }

    public List<Recipe> getPublicRecipes(Integer cookingTime, RecipeDifficulty difficulty) {
        return recipeRepository.findAllByTypeAndCookingTimeAndDifficulty(cookingTime, difficulty.name());
    }

    public List<Recipe> getPublicRecipes(String name) {
        return recipeRepository.findAllByTypeAndNameIgnoreCase(name);
    }

    public List<Recipe> getPublicRecipes(Integer cookingTime) {
        return recipeRepository.findAllByTypeAndCookingTime(cookingTime);
    }

    public List<Recipe> getPublicRecipes(RecipeDifficulty difficulty) {
        return recipeRepository.findAllByTypeAndDifficulty(difficulty.name());
    }

    public List<Recipe> getPublicRecipes() {
        return recipeRepository.findAllByType(RecipeType.PUBLIC);
    }

    public List<Recipe> getLoggedUserRecipes(String username) {
        List<Recipe> loggedUserRecipes = recipeRepository.findAllByCreatedByIgnoreCase(username);
        List<Recipe> publicRecipes = getPublicRecipes();

        return publicRecipes.addAll(loggedUserRecipes) ? publicRecipes : loggedUserRecipes;
    }

    public Recipe createRecipe(RecipeRequestDto recipeRequestDto, RecipeType recipeType, String username) {
        Recipe recipe = Recipe.builder()
                .name(recipeRequestDto.getName())
                .type(recipeType)
                .cookingTime(recipeRequestDto.getCookingTime())
                .difficulty(recipeRequestDto.getDifficulty())
                .recipeIngredients(getIngredients(recipeRequestDto))
                .createdBy(username)
                .build();

        return recipeRepository.save(recipe);
    }

    private List<RecipeIngredient> getIngredients(RecipeRequestDto recipeRequestDto) {
        return recipeRequestDto.getIngredients().stream()
                .map(recipeIngredientService::findOrInsert)
                .collect(toList());
    }

    public Recipe updateRecipe(Integer id, RecipeRequestDto recipeRequestDto, RecipeType recipeType, String username) {
        Recipe recipe = findValidRecipe(id, recipeType, username);
        recipe.toBuilder()
                .name(recipeRequestDto.getName())
                .difficulty(recipeRequestDto.getDifficulty())
                .cookingTime(recipeRequestDto.getCookingTime())
                .recipeIngredients(getIngredients(recipeRequestDto))
                .build();

        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Integer id, RecipeType recipeType, String username) {
        findValidRecipe(id, recipeType, username);

        recipeRepository.deleteByIdAndRecipeType(id, recipeType.name());
    }

    private Recipe findValidRecipe(Integer id, RecipeType recipeType, String username) {
        Optional<Recipe> optionalRecipe =
                recipeRepository.findByIdAndTypeIgnoreCaseAndCreatedByIgnoreCase(id, recipeType.name(), username);
        if (optionalRecipe.isEmpty()) {
            throw new NotFoundException(format("A {0} recipe with id {1} does not exist for user {2}",
                    recipeType.name(), id, username));
        }

        return optionalRecipe.get();
    }
}
