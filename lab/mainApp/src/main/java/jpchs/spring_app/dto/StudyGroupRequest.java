package jpchs.spring_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jpchs.spring_app.dto.inner.CoordinatesDTO;
import jpchs.spring_app.dto.inner.PersonDTO;
import jpchs.spring_app.enm.FormOfEducation;
import jpchs.spring_app.enm.Semester;

public record StudyGroupRequest(
        @NotBlank
        String name,

        @Valid
        @NotNull
        CoordinatesDTO coordinates,

        @NotNull
        @Positive
        @JsonProperty("students-count")
        Integer studentsCount,

        @Positive
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
