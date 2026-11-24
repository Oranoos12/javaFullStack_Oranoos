package be.pxl.order.client;

import be.pxl.order.dto.client.RecipeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "recipe-service", fallbackFactory = RecipeClientFallbackFactory.class)
public interface RecipeClient {

    @GetMapping("/api/recipes/{id}")
    RecipeDto findById(@PathVariable("id") Long id);
}
