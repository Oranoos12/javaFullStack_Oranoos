package be.pxl.order.dto.client;

import java.util.List;

public record RecipeDto(Long id, String name, List<RecipeIngredientDto> ingredients)  {
}
