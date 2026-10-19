package jpchs.spring_app.enm;

import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Semester {

    FIRST("Первый"),
    THIRD("Третий"),
    SEVENTH("Седьмой");

    private final String NAME;

    Semester(String name) {
        this.NAME = name;
    }

    public static Set<String> getStringSet() {
        return Arrays.stream(Semester.values())
                .map(Semester::getNAME)
                .collect(Collectors.toSet());
    }
}
