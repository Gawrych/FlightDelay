package com.flightDelay.flightdelayapi.shared.exception;

import com.flightDelay.flightdelayapi.shared.exception.importData.JsonFileImportException;
import com.flightDelay.flightdelayapi.shared.exception.request.RequestValidationException;
import com.flightDelay.flightdelayapi.shared.exception.resource.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;


@RestControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler {

    private final ResourceBundleMessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFoundException(CustomRuntimeException exception,
                                                            HttpServletRequest request) {
        return getApiErrorResponseEntity(
                exception,
                request,
                HttpStatus.NOT_FOUND,
                getLangMessage(exception));
    }


    @ExceptionHandler(RequestValidationException.class)
    public ResponseEntity<ApiError> handleBadRequestException(CustomRuntimeException exception,
                                                              HttpServletRequest request) {
        return getApiErrorResponseEntity(
                exception,
                request,
                HttpStatus.BAD_REQUEST,
                getLangMessage(exception));
    }

    @ExceptionHandler({LackOfCrucialDataException.class, JsonFileImportException.class})
    public ResponseEntity<ApiError> handleInternalServerErrorException(CustomRuntimeException exception,
                                                                       HttpServletRequest request) {
        return getApiErrorResponseEntity(
                exception,
                request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                getLangMessage(exception));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException exception,
                                                    HttpServletRequest request) {
        Optional<FieldError> errorField = Optional.ofNullable(exception.getBindingResult().getFieldError());
        String message = errorField.isPresent() ? errorField.get().getDefaultMessage() : exception.getMessage();

        return getApiErrorResponseEntity(
                exception,
                request,
                HttpStatus.BAD_REQUEST,
                message);
    }

    private String getLangMessage(CustomRuntimeException exception) {
        return messageSource.getMessage(
                exception.getErrorMessage(),
                exception.getParameters(),
                LocaleContextHolder.getLocale());
    }

    private ResponseEntity<ApiError> getApiErrorResponseEntity(Exception exception,
                                                                      HttpServletRequest request,
                                                                      HttpStatus httpStatus,
                                                                      String exceptionMessage) {
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                exception,
                exceptionMessage,
                httpStatus);

        return new ResponseEntity<>(apiError, httpStatus);
    }
}
