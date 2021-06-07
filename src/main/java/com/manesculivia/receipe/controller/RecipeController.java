package com.manesculivia.receipe.controller;

import com.manesculivia.receipe.model.Recipe;
import com.manesculivia.receipe.model.RecipeDifficulty;
import com.manesculivia.receipe.model.RecipeType;
import com.manesculivia.receipe.model.request.RecipeRequestDto;
import com.manesculivia.receipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Recipe>> getPublicRecipes(@RequestParam("name") Optional<String> name,
                                                         @RequestParam("cookingType") Optional<Integer> cookingType,
                                                         @RequestParam("difficulty") Optional<RecipeDifficulty> difficulty) {
        return new ResponseEntity<>(getFilteredRecipes(name, cookingType, difficulty), OK);
    }

    @PostMapping(value = "/public", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> createPublicRecipe(@Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        return new ResponseEntity<>(recipeService.createRecipe(recipeRequestDto, RecipeType.PUBLIC, null), CREATED);
    }

    @PutMapping(value = "/public/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> updatePublicRecipe(@PathVariable("id") Integer id,
                                                     @Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        return new ResponseEntity<>(recipeService.updateRecipe(id, recipeRequestDto, RecipeType.PUBLIC, null), OK);
    }

    @DeleteMapping("/public/{id}")
    public ResponseEntity<Void> deletePublicRecipe(@PathVariable("id") Integer id) {
        recipeService.deleteRecipe(id, RecipeType.PUBLIC, null);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping(value = "/private", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Recipe>> getPublicAndMyRecipes(Principal principal) {
        return new ResponseEntity<>(recipeService.getLoggedUserRecipes(principal.getName()), OK);
    }

    @PostMapping(value = "/private", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> createPrivateRecipe(@Valid @RequestBody RecipeRequestDto
                                                              recipeRequestDto, Principal principal) {
        return new ResponseEntity<>(recipeService.createRecipe(recipeRequestDto, RecipeType.PRIVATE, principal.getName()), CREATED);
    }

    @PutMapping(value = "/private/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> updatePrivateRecipe(@PathVariable("id") Integer id,
                                                      @Valid @RequestBody RecipeRequestDto recipeRequestDto,
                                                      Principal principal) {
        return new ResponseEntity<>(recipeService.updateRecipe(id, recipeRequestDto, RecipeType.PRIVATE, principal.getName()), OK);
    }

    @DeleteMapping("/private/{id}")
    public ResponseEntity<Void> deletePrivateRecipe(@PathVariable("id") Integer id, Principal principal) {
        recipeService.deleteRecipe(id, RecipeType.PRIVATE, principal.getName());
        return new ResponseEntity<>(NO_CONTENT);
    }

    private List<Recipe> getFilteredRecipes(Optional<String> name, Optional<Integer> cookingType, Optional<RecipeDifficulty> difficulty) {
        List<Recipe> recipes;
        if (name.isPresent() && cookingType.isPresent() && difficulty.isPresent()) {
            recipes = recipeService.getPublicRecipes(name.get(), cookingType.get(), difficulty.get());
        } else if (name.isPresent() && cookingType.isPresent()) {
            recipes = recipeService.getPublicRecipes(name.get(), cookingType.get());
        } else if (name.isPresent() && difficulty.isPresent()) {
            recipes = recipeService.getPublicRecipes(name.get(), difficulty.get());
        } else if (cookingType.isPresent() && difficulty.isPresent()) {
            recipes = recipeService.getPublicRecipes(cookingType.get(), difficulty.get());
        } else if (name.isPresent()) {
            recipes = recipeService.getPublicRecipes(name.get());
        } else if (cookingType.isPresent()) {
            recipes = recipeService.getPublicRecipes(cookingType.get());
        } else if (difficulty.isPresent()) {
            recipes = recipeService.getPublicRecipes(difficulty.get());
        } else {
            recipes = recipeService.getPublicRecipes();
        }
        return recipes;
    }

}
