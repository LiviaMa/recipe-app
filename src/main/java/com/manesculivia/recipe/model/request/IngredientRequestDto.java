package com.manesculivia.recipe.model.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@ToString
public class IngredientRequestDto {

    @NotBlank
    private String name;

    @NotNull
    // TODO handle in controller advice displaying a nice message
    // @Positive
    private Integer quantity;

}
