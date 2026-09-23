package jpchs.spring_app.mapper;

import jpchs.spring_app.dto.StudyGroupDTO;
import jpchs.spring_app.dto.StudyGroupRequest;
import jpchs.spring_app.dto.inner.ErrorItem;
import jpchs.spring_app.enm.Errors;
import jpchs.spring_app.entity.StudyGroup;
import jpchs.spring_app.exception.ApplicationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@Component
@AllArgsConstructor
public class StudyGroupMapper {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private final PersonMapper personMapper;
    private final CoordinateMapper coordinateMapper;

    public StudyGroupDTO toDTO(StudyGroup entity) {
        if (isNull(entity)) {
            return null;
        }
        return new StudyGroupDTO(
                entity.getId(),
                entity.getName(),
                coordinateMapper.toDTO(entity.getCoordinates()),
                dateFormat.format(entity.getCreationDate()),
                entity.getStudentsCount(),
                entity.getExpelledStudents(),
                entity.getFormOfEducation(),
                entity.getSemesterEnum(),
                personMapper.toDTO(entity.getGroupAdmin())
        );
    }

    public StudyGroup fromRequest(StudyGroupRequest req) {
        if (isNull(req)) {
            return null;
        }
        return new StudyGroup(
                null,
                req.name(),
                coordinateMapper.fromDTO(req.coordinates()),
                null,
                req.studentsCount(),
                req.expelledStudents(),
                req.formOfEducation(),
                req.semesterEnum(),
                personMapper.fromDTO(req.groupAdmin())
        );
    }
}
