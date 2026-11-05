package jpchs.exception;

import jpchs.dto.inner.ErrorItem;
import lombok.Getter;

import java.util.List;

@Getter
public class ApplicationException extends RuntimeException {

    List<ErrorItem> errors;
    int status;

    public ApplicationException(List<ErrorItem> errors) {
        super("");

        this.errors = errors;
        this.status = 400;
    }

    public ApplicationException(List<ErrorItem> errors, int status) {
        super("");

        this.errors = errors;
        this.status = status;
    }
}
