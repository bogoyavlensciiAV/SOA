package jpchs.spring_app.converter;

import jpchs.spring_app.exception.InvalidSortException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class LHSStringToSortConverter implements Converter<String, Sort> {
    @Override
    public Sort convert(String source) {
        try {
            var parts = source.split("\\[", 2);

            var property = parts[0];
            var direction = Sort.Direction.fromString(parts[1].replace("]", ""));

            return Sort.by(direction, property);
        } catch (IllegalArgumentException e) {
            throw new InvalidSortException(source);
        }
    }
}
