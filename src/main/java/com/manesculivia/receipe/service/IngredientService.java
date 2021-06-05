package com.manesculivia.receipe.service;

import com.manesculivia.receipe.exception.NegativeQuantityException;
import com.manesculivia.receipe.exception.NotFoundException;
import com.manesculivia.receipe.model.Ingredient;
import com.manesculivia.receipe.model.request.IngredientRequestDto;
import com.manesculivia.receipe.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient add(IngredientRequestDto ingredientRequestDto) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientRequestDto.getName());
        ingredient.setQuantity(ingredientRequestDto.getQuantity());
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateQuantity(Integer id, Integer quantity) {
        if (quantity <= 0) {
            throw new NegativeQuantityException(format("Invalid quantity: {0}. Quantity must me at least 1.", quantity));
        }

//        return ingredientRepository.findById(id)
//                .map(ingredient -> setQuantity(ingredient, quantity))
//                .map(ingredientRepository::save)
//                .orElseThrow(() -> new NotFoundException(format("Not found ingredient with id {}", id)));

        Optional<Ingredient> ingredient = ingredientRepository.findById(id);
        if (ingredient.isEmpty()) {
            throw new NotFoundException(format("Not found ingredient with id {0}", id));
        }
        ingredient.get().setQuantity(quantity);
        return ingredientRepository.save(ingredient.get());
    }

    public void delete(Integer id) {
        ingredientRepository.findById(id)
                .ifPresent(ingredientRepository::delete);
    }

//    private Ingredient setQuantity(Ingredient ingredient, Integer quantity) {
//        ingredient.setQuantity(quantity);
//        return ingredient;
//    }
}

