package jpchs.spring_app.dto.paging;

public record Filter(
        String field,
        Class<?> type,
        Operator operator,
        Object value
) {
    public enum Operator {
        EQ,
        NE,
        GT,
        GTE,
        LT,
        LTE,
        CONTAINS,
        IN
    }
}
