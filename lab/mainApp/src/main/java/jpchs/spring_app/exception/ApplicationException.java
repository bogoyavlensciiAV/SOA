package jpchs.spring_app.exception;

import jpchs.spring_app.dto.inner.ErrorItem;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ApplicationException extends RuntimeException {

    List<ErrorItem> errors;
    HttpStatus status;

    public ApplicationException(List<ErrorItem> errors) {
        super("");

        this.errors = errors;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public ApplicationException(List<ErrorItem> errors, HttpStatus status) {
        super("");

        this.errors = errors;
        this.status = status;
    }
}
