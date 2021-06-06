package com.manesculivia.receipe.controller;

import com.manesculivia.receipe.model.Recipe;
import com.manesculivia.receipe.model.RecipeType;
import com.manesculivia.receipe.model.request.RecipeRequestDto;
import com.manesculivia.receipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping(value = "/public", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Recipe>> getPublicRecipes() {
        return new ResponseEntity<>(recipeService.getAllRecipes(RecipeType.PUBLIC), OK);
    }

    @PostMapping(value = "/public", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> createPublicRecipe(@Valid @RequestBody RecipeRequestDto recipeRequestDto) {
        return new ResponseEntity<>(recipeService.createRecipe(recipeRequestDto, RecipeType.PUBLIC), CREATED);
    }

}
