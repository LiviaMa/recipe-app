package com.manesculivia.recipe.controller;

import com.manesculivia.recipe.model.PantryIngredient;
import com.manesculivia.recipe.model.request.IngredientRequestDto;
import com.manesculivia.recipe.service.PantryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/pantry/ingredients")
@RequiredArgsConstructor
public class PantryController {

    private final PantryService pantryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PantryIngredient>> getAll() {
        return new ResponseEntity<>(pantryService.getAll(), OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PantryIngredient> add(@Valid @RequestBody IngredientRequestDto ingredient) {
        return new ResponseEntity<>(pantryService.add(ingredient), CREATED);
    }

    @PutMapping(value = "/{id}/updateQuantity/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PantryIngredient> updateQuantity(@PathVariable("id") Integer id,
                                                           @PathVariable("quantity") Integer quantity) {
        return new ResponseEntity<>(pantryService.updateQuantity(id, quantity), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pantryService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }


}
