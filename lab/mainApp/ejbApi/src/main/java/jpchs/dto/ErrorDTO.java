package jpchs.dto;

import jpchs.dto.inner.ErrorItem;

import java.util.List;

public record ErrorDTO(
    List<ErrorItem> errors
) {
}
