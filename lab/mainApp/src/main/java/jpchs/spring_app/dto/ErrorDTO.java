package jpchs.spring_app.dto;

import jpchs.spring_app.dto.inner.ErrorItem;

import java.util.List;

public record ErrorDTO(
    List<ErrorItem> errors
) {
}
