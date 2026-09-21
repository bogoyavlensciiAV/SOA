package jpchs.spring_app.controller.advice;

import jpchs.spring_app.dto.inner.ErrorItem;
import jpchs.spring_app.dto.response.Error;
import jpchs.spring_app.exception.InvalidFilterException;
import jpchs.spring_app.exception.InvalidSortException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_CONTENT;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(InvalidFilterException.class)
    public ResponseEntity<Error> handleInvalidFilterException(InvalidFilterException e) {
        var item = new ErrorItem("INVALID_FILTER", e.getMessage());
        return ResponseEntity
                .status(UNPROCESSABLE_CONTENT)
                .body(new Error(List.of(item)));
    }

    @ExceptionHandler(InvalidSortException.class)
    public ResponseEntity<Error> handleInvalidSortException(InvalidSortException e) {
        var item = new ErrorItem("INVALID_SORT", e.getMessage());
        return ResponseEntity
                .status(UNPROCESSABLE_CONTENT)
                .body(new Error(List.of(item)));
    }
}
