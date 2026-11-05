package jpchs.mapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jpchs.dto.StudyGroupDTO;
import jpchs.dto.StudyGroupRequest;
import jpchs.entity.StudyGroup;
import lombok.AllArgsConstructor;

import java.text.SimpleDateFormat;

import static java.util.Objects.isNull;

@ApplicationScoped
public class StudyGroupMapper {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Inject
    private PersonMapper personMapper;

    @Inject
    private CoordinateMapper coordinateMapper;

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
