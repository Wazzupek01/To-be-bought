package com.pedrycz.tobebought;

import com.pedrycz.tobebought.exceptions.ErrorResponse;
import com.pedrycz.tobebought.exceptions.ShoppingListNotOwnedException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.NonNull;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;


@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class, ShoppingListNotOwnedException.class, NoSuchElementException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(RuntimeException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(EmptyResultDataAccessException ex) {
        ErrorResponse error = new ErrorResponse(Arrays.asList("Cannot delete non-existing resource"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex){
        ErrorResponse error = new ErrorResponse(Arrays.asList("Cannot update entity with invalid fields"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex){
        ErrorResponse error = new ErrorResponse(Arrays.asList("Body of request is invalid"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
