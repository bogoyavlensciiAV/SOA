package jpchs.isuApp.dto;

public record ErrorItem(
        Errors code,
        String message
) {}