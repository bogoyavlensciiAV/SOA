package jpchs.spring_app.converter;

import jpchs.spring_app.util.LHSDefinitionHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;

import static java.util.Objects.isNull;

@Component("sortConverter")
@RequiredArgsConstructor
public class LHSStringToSortConverter implements StringConverter<Sort>{

    private final Map<String, LHSDefinitionHelper.LHSDefinition> lhsDefinitionMap;

    public Sort convert(String source) {
        var parts = source.split("\\[", 2);

        if (parts.length != 2 || !parts[1].endsWith("]")) {
            throw new IllegalArgumentException();
        }

        var property = parts[0];

        var definition = lhsDefinitionMap.get(property);

        if (isNull(definition)) {
            throw new IllegalArgumentException();
        }

        var direction = Sort.Direction.fromString(parts[1].substring(0, parts[1].length() - 1));

        return Sort.by(direction, definition.field());
    }
}
