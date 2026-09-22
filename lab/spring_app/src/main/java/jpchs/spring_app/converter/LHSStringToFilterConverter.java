package jpchs.spring_app.converter;

import jpchs.spring_app.dto.inner.ErrorItem;
import jpchs.spring_app.dto.inner.Filter;
import jpchs.spring_app.enm.Errors;
import jpchs.spring_app.exception.ApplicationException;
import jpchs.spring_app.util.FilterDefinitionHelper;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.*;
import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class LHSStringToFilterConverter implements Converter<String, Filter> {

    private final Map<String, FilterDefinitionHelper.FilterDefinition> definitionMap;

    @Override
    public Filter convert(String source) {
        try {
            var openBracket = source.indexOf('[');
            var closeBracket = source.indexOf(']');
            var equalSign = source.indexOf('=');

            var field = source.substring(0, openBracket);
            var operatorName = source.substring(openBracket + 1, closeBracket);
            var value = source.substring(equalSign + 1);

            var definition = definitionMap.get(field);

            if (isNull(definition)) {
                throw new IllegalArgumentException();
            }

            var operator = Filter.Operator.valueOf(
                    operatorName.toUpperCase()
            );

            if (!definition.ops().contains(operator)) {
                throw new IllegalArgumentException();
            }

            var convertedValue = definition.converter().apply(value);

            return new Filter(
                    definition.field(),
                    definition.type(),
                    operator,
                    convertedValue
            );

        } catch (IllegalArgumentException e) {
            var errorMessage = "Invalid filter argument: %s";
            var error = new ErrorItem(Errors.INVALID_FILTER, errorMessage.formatted(source));
            throw new ApplicationException(List.of(error));
        }
    }
}
