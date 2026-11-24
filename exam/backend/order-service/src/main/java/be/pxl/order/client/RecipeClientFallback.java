package be.pxl.order.client;

import be.pxl.order.dto.client.RecipeDto;
import be.pxl.order.exception.RecipeNotFoundException;
import be.pxl.order.exception.ServiceUnavailableException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeClientFallback implements RecipeClient {

    private static final Logger log = LoggerFactory.getLogger(RecipeClientFallback.class);

    private final Throwable cause;

    public RecipeClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public RecipeDto findById(Long id) {
        if (isNotFound(cause)) {
            throw new RecipeNotFoundException(id);
        }
        log.warn("recipe-service onbereikbaar: {}", cause.getMessage());
        throw new ServiceUnavailableException("recipe-service");
    }

    private boolean isNotFound(Throwable error) {
        Throwable current = error;
        while (current != null) {
            if (current instanceof FeignException.NotFound) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
