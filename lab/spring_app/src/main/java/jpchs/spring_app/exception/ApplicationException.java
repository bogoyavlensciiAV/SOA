package jpchs.spring_app.exception;

import jpchs.spring_app.dto.inner.ErrorItem;
import lombok.Getter;

import java.util.List;

@Getter
public class ApplicationException extends RuntimeException {

    List<ErrorItem> errors;

    public ApplicationException(List<ErrorItem> errors) {
        super("");

        this.errors = errors;
    }
}
