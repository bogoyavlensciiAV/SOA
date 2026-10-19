package jpchs.spring_app.enm;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum FormOfEducation {

    DISTANCE_EDUCATION("Заочная"),
    FULL_TIME_EDUCATION("Очная"),
    EVENING_CLASSES("Очно-заочная");

    private final String NAME;

    FormOfEducation(String name) {
        this.NAME = name;
    }

    public static Set<String> getStringSet() {
        return Arrays.stream(FormOfEducation.values())
                .map(FormOfEducation::getNAME)
                .collect(Collectors.toSet());
    }
}
