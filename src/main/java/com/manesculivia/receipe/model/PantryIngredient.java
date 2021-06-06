package com.manesculivia.receipe.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "pantry_ingredients")
@Getter
@Setter
public class PantryIngredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ingredient_id")
    private Integer ingredientId;

    @NotBlank
    @Column(name = "ingredient_name")
    private String ingredientName;

    @Positive
    @Column(name = "ingredient_quantity")
    private Integer ingredientQuantity;

}
