package com.manesculivia.receipe.model.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
@ToString
public class IngredientRequestDto {

    @NotBlank
    private String name;

    @Positive
    private Integer quantity;

}
