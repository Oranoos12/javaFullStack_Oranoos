package be.pxl.order.exception;

import be.pxl.order.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleOrderNotFound(OrderNotFoundException e) {
        return new ErrorResponse("NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRecipeNotFound(RecipeNotFoundException e) {
        return new ErrorResponse("RECIPE_NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden(ForbiddenException e) {
        return new ErrorResponse("FORBIDDEN", e.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleInsufficientStock(InsufficientStockException e) {
        return new ErrorResponse("INSUFFICIENT_STOCK", e.getMessage());
    }

    @ExceptionHandler(SlotFullException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleSlotFull(SlotFullException e) {
        return new ErrorResponse("SLOT_FULL", e.getMessage());
    }

    @ExceptionHandler(OrderNotCancellableException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleNotCancellable(OrderNotCancellableException e) {
        return new ErrorResponse("NOT_CANCELLABLE", e.getMessage());
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse handleServiceUnavailable(ServiceUnavailableException e) {
        return new ErrorResponse("SERVICE_UNAVAILABLE", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldErrors().get(0);
        return new ErrorResponse("VALIDATION_ERROR", error.getField() + ": " + error.getDefaultMessage());
    }
}
