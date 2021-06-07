package com.manesculivia.receipe.model.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@ToString
public class IngredientRequestDto {

    @NotBlank
    private String name;

    @NotNull
    // TODO handle in controller advice displaying a nice message
    // @Positive
    private Integer quantity;

}
