package jpchs.spring_app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jpchs.spring_app.dto.inner.CoordinatesDTO;
import jpchs.spring_app.dto.inner.PersonDTO;
import jpchs.spring_app.enm.FormOfEducation;
import jpchs.spring_app.enm.Semester;

import java.util.Date;

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
