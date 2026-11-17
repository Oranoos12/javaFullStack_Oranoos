package be.pxl.recipe.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class RecipeIngredient {

    private Long ingredientId;
    private double quantityPerPerson;

    public RecipeIngredient(Long ingredientId, double quantityPerPerson) {
        this.ingredientId = ingredientId;
        this.quantityPerPerson = quantityPerPerson;
    }
}