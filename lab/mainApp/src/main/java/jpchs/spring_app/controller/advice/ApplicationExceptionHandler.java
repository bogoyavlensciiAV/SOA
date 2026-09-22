package jpchs.spring_app.controller.advice;

import jpchs.spring_app.dto.ErrorDTO;
import jpchs.spring_app.dto.inner.ErrorItem;
import jpchs.spring_app.enm.Errors;
import jpchs.spring_app.exception.ApplicationException;
import org.hibernate.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_CONTENT;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDTO> handle(ApplicationException e) {
        return ResponseEntity
                .status(UNPROCESSABLE_CONTENT)
                .body(new ErrorDTO(e.getErrors()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handle(MethodArgumentNotValidException ex) {
        var errorMessage = "Invalid field %s. Value was %s";
        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError ->
                    new ErrorItem(
                            Errors.INVALID_ARGUMENT,
                            errorMessage.formatted(fieldError.getField(), fieldError.getRejectedValue())
                    )
                )
                .toList();

        return ResponseEntity
                .status(UNPROCESSABLE_CONTENT)
                .body(new ErrorDTO(errors));
    }
}
