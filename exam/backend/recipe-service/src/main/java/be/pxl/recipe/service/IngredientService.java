package be.pxl.recipe.service;

import be.pxl.recipe.dto.response.IngredientCreatedEvent;
import be.pxl.recipe.dto.request.IngredientRequest;
import be.pxl.recipe.dto.response.IngredientResponse;
import be.pxl.recipe.exception.ForbiddenException;
import be.pxl.recipe.model.Ingredient;
import be.pxl.recipe.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IngredientService {

    private static final Logger log = LoggerFactory.getLogger(IngredientService.class);

    private final IngredientRepository ingredientRepository;
    private final RabbitTemplate rabbitTemplate;

    public IngredientService(IngredientRepository ingredientRepository, RabbitTemplate rabbitTemplate) {
        this.ingredientRepository = ingredientRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public IngredientResponse create(String role, IngredientRequest request) {
        if (!"MEDEWERKER".equals(role)) {
            throw new ForbiddenException("MEDEWERKER");
        }

        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.name());
        ingredient.setUnit(request.unit());
        Ingredient saved = ingredientRepository.save(ingredient);
        log.info("Ingrediënt {} aangemaakt met id {}", saved.getName(), saved.getId());

        try {
            rabbitTemplate.convertAndSend("ingredient.created", "",
                    new IngredientCreatedEvent(saved.getId(), saved.getName(), saved.getUnit()));
        } catch (AmqpException e) {
            log.warn("Broker onbereikbaar, event ingredient.created niet verstuurd voor ingrediënt {}", saved.getId());
        }

        return toResponse(saved);
    }

    public List<IngredientResponse> findAll() {
        List<IngredientResponse> result = new ArrayList<>();
        for (Ingredient ingredient : ingredientRepository.findAll()) {
            result.add(toResponse(ingredient));
        }
        return result;
    }

    private IngredientResponse toResponse(Ingredient ingredient) {
        return new IngredientResponse(ingredient.getId(), ingredient.getName(), ingredient.getUnit());
    }
}
