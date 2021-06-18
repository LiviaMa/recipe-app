package com.manesculivia.recipe.service;

import com.manesculivia.recipe.aspect.Logging;
import com.manesculivia.recipe.exception.NegativeQuantityException;
import com.manesculivia.recipe.exception.NotFoundException;
import com.manesculivia.recipe.model.PantryIngredient;
import com.manesculivia.recipe.model.request.IngredientRequestDto;
import com.manesculivia.recipe.repository.PantryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class PantryService {

    private final PantryRepository pantryRepository;

    public List<PantryIngredient> getAll() {
        return pantryRepository.findAll();
    }

    public PantryIngredient findIngredientForRecipe(IngredientRequestDto ingredient) {
        return pantryRepository.findByIngredientNameIgnoreCaseAndIngredientQuantityLessThanEqual(ingredient.getName(), ingredient.getQuantity())
                .orElseThrow(() -> new NotFoundException(format(
                        "{0} does not exist in fridge or pantry or the available quantity is not enough", ingredient.getName())));
    }
    @Logging
    public PantryIngredient add(IngredientRequestDto ingredientRequestDto) {
        validateQuantity(ingredientRequestDto.getQuantity());
        Optional<PantryIngredient> optionalIngredient = pantryRepository.findByIngredientNameIgnoreCase(ingredientRequestDto.getName());
        PantryIngredient pantryIngredient;
        if (optionalIngredient.isPresent()) {
            pantryIngredient = optionalIngredient.get();
            int updatedQuantity = ingredientRequestDto.getQuantity() + pantryIngredient.getIngredientQuantity();
            pantryIngredient.setIngredientQuantity(updatedQuantity);
        } else {
            pantryIngredient = new PantryIngredient();
            pantryIngredient.setIngredientName(ingredientRequestDto.getName());
            pantryIngredient.setIngredientQuantity(ingredientRequestDto.getQuantity());
        }
        return pantryRepository.save(pantryIngredient);
    }

    @Logging
    public PantryIngredient updateQuantity(Integer id, Integer quantity) {
        validateQuantity(quantity);
        Optional<PantryIngredient> ingredient = pantryRepository.findById(id);
        if (ingredient.isEmpty()) {
            throw new NotFoundException(format("Not found ingredient with id {0}", id));
        }
        ingredient.get().setIngredientQuantity(quantity);
        return pantryRepository.save(ingredient.get());
    }

    private void validateQuantity(Integer quantity) {
        if (quantity <= 0) {
            throw new NegativeQuantityException(format("Invalid quantity: {0}. Quantity must me at least 1.", quantity));
        }
    }

    public void delete(Integer id) {
        pantryRepository.findById(id)
                .ifPresent(pantryRepository::delete);
    }

}

