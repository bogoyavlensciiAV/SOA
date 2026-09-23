package jpchs.spring_app.dto;

import jpchs.spring_app.dto.paging.Filter;

import java.util.Set;

public record LHSDefinitionDTO(
        String field,
        Set<Filter.Operator> allowedOperators,
        Class<?> type
) {
}
