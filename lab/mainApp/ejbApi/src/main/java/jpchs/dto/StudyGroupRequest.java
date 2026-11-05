package jpchs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jpchs.dto.inner.CoordinatesDTO;
import jpchs.dto.inner.PersonDTO;
import jpchs.enm.FormOfEducation;
import jpchs.enm.Semester;

public record StudyGroupRequest(
        @NotBlank
        String name,

        @Valid
        @NotNull
        CoordinatesDTO coordinates,

        @NotNull
        @PositiveOrZero
        @JsonProperty("students-count")
        Integer studentsCount,

        @PositiveOrZero
        @JsonProperty("expelled-students")
        Integer expelledStudents,

        @NotNull
        @JsonProperty("form-of-education")
        FormOfEducation formOfEducation,

        @JsonProperty("semester-enum")
        Semester semesterEnum,

        @Valid
        @JsonProperty("group-admin")
        PersonDTO groupAdmin
) {
}
