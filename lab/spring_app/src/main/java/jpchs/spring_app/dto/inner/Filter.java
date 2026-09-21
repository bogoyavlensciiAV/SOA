package jpchs.spring_app.dto.inner;

public record Filter(
        String field,
        Operator operator,
        String value
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
