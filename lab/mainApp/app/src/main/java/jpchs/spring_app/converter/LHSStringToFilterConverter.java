package jpchs.spring_app.converter;

import jpchs.dto.paging.Filter;
import jpchs.spring_app.util.LHSDefinitionHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Component("filterConverter")
@RequiredArgsConstructor
public class LHSStringToFilterConverter implements StringConverter<Filter>{

    private final Map<String, LHSDefinitionHelper.LHSDefinition> definitionMap;

    public Filter convert(String source) {
        var regex = Pattern.compile("^(?<field>[^\\[\\]=]+)\\[(?<op>[^]]*)]=(?<value>.*)$");
        var matcher = regex.matcher(source);

        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }

        var field = matcher.group("field");
        var operatorName = matcher.group("op");
        var value = matcher.group("value");

        var definition = definitionMap.get(field);

        var operator = Filter.Operator.valueOf(
                operatorName.toUpperCase()
        );

        if (isNull(definition) || !definition.ops().contains(operator)) {
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
