package be.pxl.recipe.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recipes")
@Getter
@Setter
@NoArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int prepTimeMinutes;
    private int persons;
    private String week;

    @ElementCollection
    @CollectionTable(name = "recipe_allergens")
    @Enumerated(EnumType.STRING)
    private Set<Allergen> allergens = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "recipe_ingredients")
    private List<RecipeIngredient> ingredients = new ArrayList<>();
}