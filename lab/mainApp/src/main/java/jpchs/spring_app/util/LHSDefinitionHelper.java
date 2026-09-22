package jpchs.spring_app.util;

import jpchs.spring_app.dto.paging.Filter;
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

import static jpchs.spring_app.dto.paging.Filter.Operator.*;

@Component
public class LHSDefinitionHelper {

    @Bean
    private Map<String, LHSDefinition> lhsDefinitionMap() {
        return Map.ofEntries(
                // StudyGroup
                Map.entry(
                        "id",
                        new LHSDefinition(
                                "id",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Id"
                        )
                ),
                Map.entry(
                        "name",
                        new LHSDefinition(
                                "name",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS),
                                "Name"
                        )
                ),
                Map.entry(
                        "creation-date",
                        new LHSDefinition(
                                "creationDate",
                                Date.class,
                                this::parseDate,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Creation date"
                        )
                ),
                Map.entry(
                        "students-count",
                        new LHSDefinition(
                                "studentsCount",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Student count"
                        )
                ),
                Map.entry(
                        "expelled-students",
                        new LHSDefinition(
                                "expelledStudents",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Expelled students"
                        )
                ),
                Map.entry(
                        "form-of-education",
                        new LHSDefinition(
                                "formOfEducation",
                                FormOfEducation.class,
                                value -> FormOfEducation.valueOf(
                                        value.toUpperCase()
                                ),
                                Set.of(EQ, NE),
                                "Form of education"
                        )
                ),
                Map.entry(
                        "semester-enum",
                        new LHSDefinition(
                                "semesterEnum",
                                Semester.class,
                                value -> Semester.valueOf(
                                        value.toUpperCase()
                                ),
                                Set.of(EQ, NE),
                                "Semester number"
                        )
                ),

                // Coordinates
                Map.entry(
                        "coordinates.x",
                        new LHSDefinition(
                                "coordinates.x",
                                Long.class,
                                Long::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "X coordinate"
                        )
                ),
                Map.entry(
                        "coordinates.y",
                        new LHSDefinition(
                                "coordinates.y",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Y coordinate"
                        )
                ),

                // Person
                Map.entry(
                        "group-admin.name",
                        new LHSDefinition(
                                "groupAdmin.name",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS),
                                "Group admin's name"
                        )
                ),
                Map.entry(
                        "group-admin.weight",
                        new LHSDefinition(
                                "groupAdmin.weight",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Group admin's weight"
                        )
                ),
                Map.entry(
                        "group-admin.passportID",
                        new LHSDefinition(
                                "groupAdmin.passportID",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS),
                                "Group admin's passportID"
                        )
                ),

                // Location
                Map.entry(
                        "group-admin.location.x",
                        new LHSDefinition(
                                "groupAdmin.location.x",
                                Long.class,
                                Long::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "X coordinate of location of group admin"
                        )
                ),
                Map.entry(
                        "group-admin.location.y",
                        new LHSDefinition(
                                "groupAdmin.location.y",
                                Long.class,
                                Long::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Y coordinate of location of group admin"
                        )
                ),

                Map.entry(
                        "group-admin.location.name",
                        new LHSDefinition(
                                "groupAdmin.location.name",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS),
                                "The name of location of group admin"
                        )
                )
        );
    }

    public record LHSDefinition(
            String field,
            Class<?> type,
            Function<String, ?> converter,
            Set<Filter.Operator> ops,
            String humanReadable
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
