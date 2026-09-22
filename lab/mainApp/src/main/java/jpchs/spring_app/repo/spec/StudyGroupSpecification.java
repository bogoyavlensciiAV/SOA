package jpchs.spring_app.repo.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jpchs.spring_app.dto.paging.Filter;
import jpchs.spring_app.entity.StudyGroup;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudyGroupSpecification {

    private Specification<StudyGroup> from(Filter filter) {
        return (root, query, cb) ->
                build(cb, resolvePath(root, filter.field()), filter.operator(), filter.type(), filter.value());
    }

    public Specification<StudyGroup> from(List<Filter> filters) {
        return filters.stream()
                .map(this::from)
                .reduce(Specification::and)
                .orElse(null);
    }

    private Path<?> resolvePath(Root<StudyGroup> root, String field) {
        var path = (Path<?>) root;
        for (String part : field.split("\\.")) {
            path = path.get(part);
        }
        return path;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> Predicate build(CriteriaBuilder cb, Path<?> rawPath, Filter.Operator op, Class<T> type, Object value) {
        var path = (Path<T>) rawPath;
        var typedValue = type.cast(value);

        return switch (op) {
            case EQ -> cb.equal(path, typedValue);
            case NE -> cb.notEqual(path, typedValue);
            case CONTAINS -> {
                if (type != String.class) {
                    throw new IllegalArgumentException("CONTAINS применим только к String");
                }
                yield cb.like(cb.lower((Path<String>) path), "%" + ((String) typedValue).toLowerCase() + "%");
            }
            case GT, GTE, LT, LTE -> {
                if (!Comparable.class.isAssignableFrom(type)) {
                    throw new IllegalArgumentException(op + " требует Comparable тип");
                }
                yield compare(cb, (Path<Comparable>) path, op, (Comparable) typedValue);
            }
            case IN -> throw new UnsupportedOperationException("IN не реализован");
        };
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Predicate compare(CriteriaBuilder cb, Path<Comparable> path, Filter.Operator op, Comparable value) {
        return switch (op) {
            case GT -> cb.greaterThan(path, value);
            case GTE -> cb.greaterThanOrEqualTo(path, value);
            case LT -> cb.lessThan(path, value);
            case LTE -> cb.lessThanOrEqualTo(path, value);
            default -> throw new IllegalStateException();
        };
    }
}
