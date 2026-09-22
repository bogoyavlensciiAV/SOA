package jpchs.spring_app.converter;

import jpchs.spring_app.dto.paging.Filter;
import jpchs.spring_app.util.LHSDefinitionHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import static java.util.Objects.isNull;

@Component("filterConverter")
@RequiredArgsConstructor
public class LHSStringToFilterConverter implements StringConverter<Filter>{

    private final Map<String, LHSDefinitionHelper.LHSDefinition> definitionMap;

    public Filter convert(String source) {
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
    }
}
