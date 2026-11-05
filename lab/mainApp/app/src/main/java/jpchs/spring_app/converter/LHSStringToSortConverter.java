package jpchs.spring_app.converter;

import jpchs.dto.paging.SortOrder;
import jpchs.spring_app.util.LHSDefinitionHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@Component("sortConverter")
@RequiredArgsConstructor
public class LHSStringToSortConverter implements StringConverter<SortOrder>{

    private final Map<String, LHSDefinitionHelper.LHSDefinition> lhsDefinitionMap;

    public SortOrder convert(String source) {
        var regex = Pattern.compile("^(?<field>[^\\[\\]]+)\\[(?<dir>[^]]*)]$");
        var matcher = regex.matcher(source);

        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }

        var field = matcher.group("field");
        var dir = matcher.group("dir");

        var definition = lhsDefinitionMap.get(field);

        if (isNull(definition)) {
            throw new IllegalArgumentException();
        }

        var direction = SortOrder.Direction.fromString(dir);

        return new SortOrder(definition.field(), direction);
    }
}
