package jpchs.spring_app.converter;

import jpchs.spring_app.dto.inner.Filter;
import jpchs.spring_app.exception.InvalidFilterException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
class LHSStringToFilterConverter implements Converter<String, Filter> {

    @Override
    public Filter convert(String source) {
        try {
            var openBracket = source.indexOf('[');
            var closeBracket = source.indexOf(']');
            var equalSign = source.indexOf('=');

            var field = source.substring(0, openBracket);
            var operator = source.substring(openBracket + 1, closeBracket);
            var value = source.substring(equalSign + 1);

            return new Filter(
                    field,
                    Filter.Operator.valueOf(operator.toUpperCase()),
                    value
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidFilterException(source);
        }
    }
}
