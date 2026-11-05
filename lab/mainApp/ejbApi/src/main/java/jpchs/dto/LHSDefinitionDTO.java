package jpchs.dto;

import jpchs.dto.paging.Filter;

import java.util.Set;

public record LHSDefinitionDTO(
        String field,
        Set<Filter.Operator> allowedOperators,
        Class<?> type,
        Set<String> allowedEnum,
        String humanReadable
) {
}
