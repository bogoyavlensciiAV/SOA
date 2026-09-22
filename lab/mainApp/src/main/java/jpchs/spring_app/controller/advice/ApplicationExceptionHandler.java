package jpchs.spring_app.controller.advice;

import jpchs.spring_app.dto.ErrorDTO;
import jpchs.spring_app.dto.inner.ErrorItem;
import jpchs.spring_app.enm.Errors;
import jpchs.spring_app.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_CONTENT;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDTO> handle(ApplicationException e) {
        return ResponseEntity
                .status(e.getStatus())
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

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorDTO> handle(HandlerMethodValidationException ex) {
        var errorMessage = "Invalid value for %s: %s";
        var errors = ex.getParameterValidationResults().stream()
                .map(v -> new ErrorItem(Errors.INVALID_ARGUMENT,
                        errorMessage.formatted(v.getArgument(),
                                v.getResolvableErrors().stream()
                                        .map(MessageSourceResolvable::getDefaultMessage)
                                        .collect(Collectors.joining(", "))
                        )))
                .toList();

        return ResponseEntity.status(UNPROCESSABLE_CONTENT).body(new ErrorDTO(errors));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDTO> handle(MethodArgumentTypeMismatchException ex) {
        var errorMessage = "Failed to convert value: %s";
        var errors = List.of(
                new ErrorItem(
                        Errors.REQUEST_TYPE_MISSMATCH,
                        errorMessage.formatted(ex.getName())
                )
        );

        return ResponseEntity.status(BAD_REQUEST).body(new ErrorDTO(errors));
    }
}
