package com.manesculivia.receipe.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private RecipeType type;

    private Integer cookingTime;

    @Enumerated(EnumType.STRING)
    private RecipeDifficulty difficulty;

    private String createdBy;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "recipes_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = " ingredient_id"))
    private List<Ingredient> ingredients;
}
