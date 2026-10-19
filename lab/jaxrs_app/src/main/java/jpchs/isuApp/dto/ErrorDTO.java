package jpchs.isuApp.dto;

import java.util.List;

public record ErrorDTO(
        List<ErrorItem> errors
) {}