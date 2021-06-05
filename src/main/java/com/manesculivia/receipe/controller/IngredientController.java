package com.manesculivia.receipe.controller;

import com.manesculivia.receipe.model.Ingredient;
import com.manesculivia.receipe.model.request.IngredientRequestDto;
import com.manesculivia.receipe.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ingredient>> getAll() {
        return new ResponseEntity<>(ingredientService.getAll(), OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ingredient> add(@Valid @RequestBody IngredientRequestDto ingredient) {
        Ingredient createdIngredient = ingredientService.add(ingredient);
        return new ResponseEntity<>(createdIngredient, CREATED);
    }

    @PutMapping(value = "/{id}/updateQuantity/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ingredient> updateQuantity(@PathVariable("id") Integer id,
                                                     @PathVariable("quantity") Integer quantity) {
        Ingredient updatedIngredient = ingredientService.updateQuantity(id, quantity);
        return new ResponseEntity<>(updatedIngredient, OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ingredientService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
