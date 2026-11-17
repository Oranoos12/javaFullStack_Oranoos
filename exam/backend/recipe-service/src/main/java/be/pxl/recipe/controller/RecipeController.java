package be.pxl.recipe.controller;

import be.pxl.recipe.dto.request.RecipeRequest;
import be.pxl.recipe.dto.response.RecipeResponse;
import be.pxl.recipe.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public List<RecipeResponse> findAll(@RequestParam(required = false) String week) {
        return recipeService.findAll(week);
    }

    @GetMapping("/{id}")
    public RecipeResponse findById(@PathVariable Long id) {
        return recipeService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeResponse create(@RequestHeader(value = "X-User-Role", required = false) String role,
                                 @Valid @RequestBody RecipeRequest request) {
        return recipeService.create(role, request);
    }

    @PutMapping("/{id}")
    public RecipeResponse update(@RequestHeader(value = "X-User-Role", required = false) String role,
                                 @PathVariable Long id,
                                 @Valid @RequestBody RecipeRequest request) {
        return recipeService.update(role, id, request);
    }
}