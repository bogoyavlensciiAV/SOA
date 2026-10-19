package jpchs.spring_app.util;

import jpchs.spring_app.dto.paging.Filter;
import jpchs.spring_app.enm.FormOfEducation;
import jpchs.spring_app.enm.Semester;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
                                "Имя"
                        )
                ),
                Map.entry(
                        "creation-date",
                        new LHSDefinition(
                                "creationDate",
                                Date.class,
                                this::parseDate,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Дата создания"
                        )
                ),
                Map.entry(
                        "students-count",
                        new LHSDefinition(
                                "studentsCount",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Количество студентов"
                        )
                ),
                Map.entry(
                        "expelled-students",
                        new LHSDefinition(
                                "expelledStudents",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Исключенные студенты"
                        )
                ),
                Map.entry(
                        "form-of-education",
                        new LHSDefinition(
                                "formOfEducation",
                                FormOfEducation.class,
                                FormOfEducation.getStringSet(),
                                value -> FormOfEducation.valueOf(
                                        value.toUpperCase()
                                ),
                                Set.of(EQ, NE),
                                "Форма обучения"
                        )
                ),
                Map.entry(
                        "semester-enum",
                        new LHSDefinition(
                                "semesterEnum",
                                Semester.class,
                                Semester.getStringSet(),
                                value -> Semester.valueOf(
                                        value.toUpperCase()
                                ),
                                Set.of(EQ, NE),
                                "Номер семестра"
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
                                "Координата X"
                        )
                ),
                Map.entry(
                        "coordinates.y",
                        new LHSDefinition(
                                "coordinates.y",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Координата Y"
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
                                "Имя админа"
                        )
                ),
                Map.entry(
                        "group-admin.weight",
                        new LHSDefinition(
                                "groupAdmin.weight",
                                Integer.class,
                                Integer::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Вес админа"
                        )
                ),
                Map.entry(
                        "group-admin.passportID",
                        new LHSDefinition(
                                "groupAdmin.passportID",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS),
                                "Номер паспорта админа"
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
                                "Координата X админа"
                        )
                ),
                Map.entry(
                        "group-admin.location.y",
                        new LHSDefinition(
                                "groupAdmin.location.y",
                                Long.class,
                                Long::valueOf,
                                Set.of(EQ, NE, GT, GTE, LT, LTE),
                                "Координата Y админа"
                        )
                ),

                Map.entry(
                        "group-admin.location.name",
                        new LHSDefinition(
                                "groupAdmin.location.name",
                                String.class,
                                Function.identity(),
                                Set.of(EQ, NE, CONTAINS),
                                "Название локации админа"
                        )
                )
        );
    }

    public record LHSDefinition(
            String field,
            Class<?> type,
            Set<String> allowedEnum,
            Function<String, ?> converter,
            Set<Filter.Operator> ops,
            String humanReadable
    ) {
        LHSDefinition(String field,
                      Class<?> type,
                      Function<String, ?> converter,
                      Set<Filter.Operator> ops,
                      String humanReadable) {
            this(field, type, null, converter, ops, humanReadable);
        }
    }


    private Date parseDate(String value) {
        try {
            if (!Pattern.matches("^\\d{2}-\\d{2}-\\d{4}$", value)) {
                throw new IllegalArgumentException();
            }
            return new SimpleDateFormat("dd-MM-yyyy").parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "Invalid date: " + value,
                    e
            );
        }
    }
}
