package com.tp1.monolito.shared.api;

import com.tp1.monolito.shared.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ApiError body = ApiError.of(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::fieldErrorToMessage)
                .collect(Collectors.joining("; "));

        ApiError body = ApiError.of(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBodyParse(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message = "Corpo da requisicao invalido. Verifique formatos de data e valores de enum.";
        ApiError body = ApiError.of(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex, HttpServletRequest request) {
        ApiError body = ApiError.of(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno inesperado.",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    private String fieldErrorToMessage(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}

