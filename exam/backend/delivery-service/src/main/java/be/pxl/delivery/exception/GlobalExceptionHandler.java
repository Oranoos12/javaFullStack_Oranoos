package be.pxl.delivery.exception;

import be.pxl.delivery.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RunNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRunNotFound(RunNotFoundException e) {
        return new ErrorResponse("NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(StopNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleStopNotFound(StopNotFoundException e) {
        return new ErrorResponse("NOT_FOUND", e.getMessage());
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden(ForbiddenException e) {
        return new ErrorResponse("FORBIDDEN", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException e) {
        FieldError error = e.getBindingResult().getFieldErrors().get(0);
        return new ErrorResponse("VALIDATION_ERROR", error.getField() + ": " + error.getDefaultMessage());
    }
}
