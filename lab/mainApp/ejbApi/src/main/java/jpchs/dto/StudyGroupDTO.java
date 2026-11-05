package jpchs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jpchs.dto.inner.CoordinatesDTO;
import jpchs.dto.inner.PersonDTO;
import jpchs.enm.FormOfEducation;
import jpchs.enm.Semester;

public record StudyGroupDTO(
        Integer id,
        String name,
        CoordinatesDTO coordinates,
        @JsonProperty("creation-date")
        String creationDate,
        @JsonProperty("students-count")
        int studentsCount,
        @JsonProperty("expelled-students")
        Integer expelledStudents,
        @JsonProperty("form-of-education")
        FormOfEducation formOfEducation,
        @JsonProperty("semester-enum")
        Semester semesterEnum,
        @JsonProperty("group-admin")
        PersonDTO groupAdmin
) {
}
