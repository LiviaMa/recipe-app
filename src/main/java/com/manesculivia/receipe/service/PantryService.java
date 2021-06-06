package com.manesculivia.receipe.service;

import com.manesculivia.receipe.exception.NegativeQuantityException;
import com.manesculivia.receipe.exception.NotFoundException;
import com.manesculivia.receipe.model.PantryIngredient;
import com.manesculivia.receipe.model.request.IngredientRequestDto;
import com.manesculivia.receipe.repository.PantryRepository;
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

    public PantryIngredient add(IngredientRequestDto ingredientRequestDto) {
        PantryIngredient pantryIngredient = new PantryIngredient();
        pantryIngredient.setIngredientName(ingredientRequestDto.getName());
        pantryIngredient.setIngredientQuantity(ingredientRequestDto.getQuantity());
        return pantryRepository.save(pantryIngredient);
    }

    public PantryIngredient updateQuantity(Integer id, Integer quantity) {
        if (quantity <= 0) {
            throw new NegativeQuantityException(format("Invalid quantity: {0}. Quantity must me at least 1.", quantity));
        }
        Optional<PantryIngredient> ingredient = pantryRepository.findById(id);
        if (ingredient.isEmpty()) {
            throw new NotFoundException(format("Not found ingredient with id {0}", id));
        }
        ingredient.get().setIngredientQuantity(quantity);
        return pantryRepository.save(ingredient.get());
    }

    public void delete(Integer id) {
        pantryRepository.findById(id)
                .ifPresent(pantryRepository::delete);
    }

}

