package be.pxl.recipe.controller;

import be.pxl.recipe.dto.request.IngredientRequest;
import be.pxl.recipe.dto.response.IngredientResponse;
import be.pxl.recipe.service.IngredientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientResponse create(@RequestHeader(value = "X-User-Role", required = false) String role,
                                     @Valid @RequestBody IngredientRequest request) {
        return ingredientService.create(role, request);
    }

    @GetMapping
    public List<IngredientResponse> findAll() {
        return ingredientService.findAll();
    }
}