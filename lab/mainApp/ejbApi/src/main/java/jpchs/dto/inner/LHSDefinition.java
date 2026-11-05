package jpchs.dto.inner;

import jpchs.dto.paging.Filter;

import java.util.Set;
import java.util.function.Function;

public record LHSDefinition(
        String field,
        Class<?> type,
        Set<String> allowedEnum,
        Function<String, ?> converter,
        Set<Filter.Operator> ops,
        String humanReadable
) {
    LHSDefinition(String field,
                  Class<?> type,
                  Function<String, ?> converter,
                  Set<Filter.Operator> ops,
                  String humanReadable) {
        this(field, type, null, converter, ops, humanReadable);
    }
}
