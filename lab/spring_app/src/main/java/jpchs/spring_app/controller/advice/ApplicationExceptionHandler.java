package jpchs.spring_app.controller.advice;

import jpchs.spring_app.dto.ErrorDTO;
import jpchs.spring_app.exception.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_CONTENT;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorDTO> handle(ApplicationException e) {
        return ResponseEntity
                .status(UNPROCESSABLE_CONTENT)
                .body(new ErrorDTO(e.getErrors()));
    }
}
