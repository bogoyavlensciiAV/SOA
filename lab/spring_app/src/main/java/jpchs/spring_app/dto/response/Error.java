package jpchs.spring_app.dto.response;

import jpchs.spring_app.dto.inner.ErrorItem;

import java.util.List;

public record Error(
    List<ErrorItem> errors
) {
}
