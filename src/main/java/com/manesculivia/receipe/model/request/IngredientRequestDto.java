package com.manesculivia.receipe.model.request;

import com.manesculivia.receipe.model.Ingredient;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class IngredientRequestDto {

    @NotBlank
    private String name;

    @Positive
    private Integer quantity;

}
