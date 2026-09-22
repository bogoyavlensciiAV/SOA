package jpchs.spring_app.util;

import jpchs.spring_app.dto.inner.Filter;
import jpchs.spring_app.enm.FormOfEducation;
import jpchs.spring_app.enm.Semester;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static jpchs.spring_app.dto.inner.Filter.Operator.*;

@Component
public class FilterDefinitionHelper {

    @Bean
    private Map<String, FilterDefinition> definitionMap() {
        return Map.ofEntries(
                // StudyGroup
                Map.entry(
                        "id",
                        new FilterDefinition(
                                "id",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE)
                        )
                ),
                Map.entry(
                        "name",
                        new FilterDefinition(
                                "name",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS)
                        )
                ),
                Map.entry(
                        "creationDate",
                        new FilterDefinition(
                                "creationDate",
                                Date.class,
                                this::parseDate,
                                Set.of(EQ, NE, GT, GTE, LT, LTE)
                        )
                ),
                Map.entry(
                        "studentsCount",
                        new FilterDefinition(
                                "studentsCount",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE)
                        )
                ),
                Map.entry(
                        "expelledStudents",
                        new FilterDefinition(
                                "expelledStudents",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE)
                        )
                ),
                Map.entry(
                        "formOfEducation",
                        new FilterDefinition(
                                "formOfEducation",
                                FormOfEducation.class,
                                value -> FormOfEducation.valueOf(
                                        value.toUpperCase()
                                ),
                                Set.of(EQ, NE)
                        )
                ),
                Map.entry(
                        "semesterEnum",
                        new FilterDefinition(
                                "semesterEnum",
                                Semester.class,
                                value -> Semester.valueOf(
                                        value.toUpperCase()
                                ),
                                Set.of(EQ, NE)
                        )
                ),

                // Coordinates
                Map.entry(
                        "coordinates_x",
                        new FilterDefinition(
                                "coordinates.x",
                                Long.class,
                                Long::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE)
                        )
                ),
                Map.entry(
                        "coordinates_y",
                        new FilterDefinition(
                                "coordinates.y",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE)
                        )
                ),

                // Person
                Map.entry(
                        "groupAdmin_name",
                        new FilterDefinition(
                                "groupAdmin.name",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS)
                        )
                ),
                Map.entry(
                        "groupAdmin_weight",
                        new FilterDefinition(
                                "groupAdmin.weight",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE)
                        )
                ),
                Map.entry(
                        "groupAdmin_passportID",
                        new FilterDefinition(
                                "groupAdmin.passportID",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS)
                        )
                ),

                // Location
                Map.entry(
                        "groupAdmin_location_x",
                        new FilterDefinition(
                                "groupAdmin.location.x",
                                Long.class,
                                Long::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE)
                        )
                ),
                Map.entry(
                        "groupAdmin_location_y",
                        new FilterDefinition(
                                "groupAdmin.location.y",
                                Long.class,
                                Long::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE)
                        )
                ),

                Map.entry(
                        "groupAdmin_location_name",
                        new FilterDefinition(
                                "groupAdmin.location.name",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS)
                        )
                )
        );
    }

    public record FilterDefinition(
            String field,
            Class<?> type,
            Function<String, ?> converter,
            Set<Filter.Operator> ops
    ) {
    }


    private Date parseDate(String value) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "Invalid date: " + value,
                    e
            );
        }
    }
}
