package jpchs.repo.spec;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.*;

import jpchs.dto.paging.Filter;
import jpchs.entity.StudyGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class StudyGroupSpecification {

    public static class PathResolver {

        private final Root<StudyGroup> root;

        private final Map<String, From<?, ?>> joins =
                new HashMap<>();

        public PathResolver(Root<StudyGroup> root) {
            this.root = root;
        }

        public Path<?> resolve(String field) {

            if (field == null || field.isBlank()) {
                throw new IllegalArgumentException(
                        "Имя поля не может быть пустым"
                );
            }

            String[] parts = field.split("\\.");

            Path<?> path = root;
            From<?, ?> from = root;

            StringBuilder prefix = new StringBuilder();

            for (int i = 0; i < parts.length; i++) {

                String part = parts[i];

                if (i > 0) {
                    prefix.append('.');
                }

                prefix.append(part);

                if ("groupAdmin".equals(part) && i == 0) {

                    String key = prefix.toString();
                    From<?, ?> parent = from;

                    from = joins.computeIfAbsent(
                            key,
                            k -> parent.join(
                                    part,
                                    JoinType.LEFT
                            )
                    );

                    path = from;

                } else {
                    path = path.get(part);
                }
            }

            return path;
        }
    }

    public List<Predicate> toPredicates(
            CriteriaBuilder cb,
            PathResolver resolver,
            List<Filter> filters
    ) {
        return filters.stream()
                .map(f -> build(
                        cb,
                        resolver.resolve(f.field()),
                        f.operator(),
                        f.type(),
                        f.value()
                ))
                .toList();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> Predicate build(
            CriteriaBuilder cb,
            Path<?> rawPath,
            Filter.Operator op,
            Class<T> type,
            Object value
    ) {
        Path<T> path = (Path<T>) rawPath;
        T typedValue = type.cast(value);

        return switch (op) {

            case EQ -> cb.equal(path, typedValue);

            case NE -> cb.notEqual(path, typedValue);

            case CONTAINS -> {

                if (type != String.class) {
                    throw new IllegalArgumentException(
                            "CONTAINS применим только к String"
                    );
                }

                yield cb.like(
                        cb.lower((Path<String>) path),
                        "%" + ((String) typedValue).toLowerCase() + "%"
                );
            }

            case GT, GTE, LT, LTE -> {

                if (!Comparable.class.isAssignableFrom(type)) {
                    throw new IllegalArgumentException(
                            op + " требует Comparable тип"
                    );
                }

                yield compare(
                        cb,
                        (Path<Comparable>) path,
                        op,
                        (Comparable) typedValue
                );
            }

            case IN -> throw new UnsupportedOperationException(
                    "IN не реализован"
            );
        };
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Predicate compare(
            CriteriaBuilder cb,
            Path<Comparable> path,
            Filter.Operator op,
            Comparable value
    ) {
        return switch (op) {

            case GT -> cb.greaterThan(path, value);

            case GTE -> cb.greaterThanOrEqualTo(path, value);

            case LT -> cb.lessThan(path, value);

            case LTE -> cb.lessThanOrEqualTo(path, value);

            default -> throw new IllegalStateException(
                    "Неподдерживаемый оператор: " + op
            );
        };
    }
}
