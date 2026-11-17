package be.pxl.recipe.service;

import be.pxl.recipe.dto.request.RecipeIngredientRequest;
import be.pxl.recipe.dto.response.RecipeIngredientResponse;
import be.pxl.recipe.dto.request.RecipeRequest;
import be.pxl.recipe.dto.response.RecipeResponse;
import be.pxl.recipe.exception.ForbiddenException;
import be.pxl.recipe.exception.IngredientNotFoundException;
import be.pxl.recipe.exception.RecipeNotFoundException;
import be.pxl.recipe.model.Allergen;
import be.pxl.recipe.model.Ingredient;
import be.pxl.recipe.model.Recipe;
import be.pxl.recipe.model.RecipeIngredient;
import be.pxl.recipe.repository.IngredientRepository;
import be.pxl.recipe.repository.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public RecipeService(RecipeRepository recipeRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Transactional(readOnly = true)
    public List<RecipeResponse> findAll(String week) {
        List<Recipe> recipes;
        if (week == null || week.isBlank()) {
            recipes = recipeRepository.findAll();
        } else {
            recipes = recipeRepository.findByWeek(week);
        }

        List<RecipeResponse> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            result.add(toResponse(recipe));
        }
        return result;
    }

    @Transactional(readOnly = true)
    public RecipeResponse findById(Long id) {
        return toResponse(getRecipe(id));
    }

    @Transactional
    public RecipeResponse create(String role, RecipeRequest request) {
        checkMedewerker(role);
        Recipe recipe = new Recipe();
        copyRequest(request, recipe);
        Recipe saved = recipeRepository.save(recipe);
        log.info("Recept {} aangemaakt met id {}", saved.getName(), saved.getId());
        return toResponse(saved);
    }

    @Transactional
    public RecipeResponse update(String role, Long id, RecipeRequest request) {
        checkMedewerker(role);
        Recipe recipe = getRecipe(id);
        copyRequest(request, recipe);
        Recipe saved = recipeRepository.save(recipe);
        log.info("Recept {} aangepast", saved.getId());
        return toResponse(saved);
    }

    private void checkMedewerker(String role) {
        if (!"MEDEWERKER".equals(role)) {
            throw new ForbiddenException("MEDEWERKER");
        }
    }

    private Recipe getRecipe(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new RecipeNotFoundException(id));
    }

    private void copyRequest(RecipeRequest request, Recipe recipe) {
        recipe.setName(request.name());
        recipe.setDescription(request.description());
        recipe.setPrepTimeMinutes(request.prepTimeMinutes());
        recipe.setPersons(request.persons());
        recipe.setWeek(request.week());

        recipe.getAllergens().clear();
        if (request.allergens() != null) {
            recipe.getAllergens().addAll(request.allergens());
        }

        recipe.getIngredients().clear();
        for (RecipeIngredientRequest item : request.ingredients()) {
            if (!ingredientRepository.existsById(item.ingredientId())) {
                throw new IngredientNotFoundException(item.ingredientId());
            }
            recipe.getIngredients().add(new RecipeIngredient(item.ingredientId(), item.quantityPerPerson()));
        }
    }

    private RecipeResponse toResponse(Recipe recipe) {
        List<RecipeIngredientResponse> ingredients = new ArrayList<>();
        for (RecipeIngredient item : recipe.getIngredients()) {
            Ingredient ingredient = ingredientRepository.findById(item.getIngredientId())
                    .orElseThrow(() -> new IngredientNotFoundException(item.getIngredientId()));
            ingredients.add(new RecipeIngredientResponse(
                    ingredient.getId(),
                    ingredient.getName(),
                    ingredient.getUnit(),
                    item.getQuantityPerPerson()));
        }

        List<Allergen> allergens = new ArrayList<>(recipe.getAllergens());

        return new RecipeResponse(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getPrepTimeMinutes(),
                recipe.getPersons(),
                recipe.getWeek(),
                allergens,
                ingredients);
    }
}