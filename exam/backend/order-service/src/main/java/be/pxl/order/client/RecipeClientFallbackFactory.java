package be.pxl.order.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RecipeClientFallbackFactory implements FallbackFactory<RecipeClient> {

    @Override
    public RecipeClient create(Throwable cause) {
        return new RecipeClientFallback(cause);
    }
}
