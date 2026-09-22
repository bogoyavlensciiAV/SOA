package jpchs.spring_app.mapper;

import jpchs.spring_app.dto.StudyGroupDTO;
import jpchs.spring_app.entity.StudyGroup;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.util.Objects.isNull;

@Component
@AllArgsConstructor
public class StudyGroupMapper {

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
                entity.getCreationDate(),
                entity.getStudentsCount(),
                entity.getExpelledStudents(),
                entity.getFormOfEducation(),
                entity.getSemesterEnum(),
                personMapper.toDTO(entity.getGroupAdmin())
        );
    }

    public StudyGroup fromDTO(StudyGroupDTO dto) {
        if (isNull(dto)) {
            return null;
        }
        return new StudyGroup(
                dto.id(),
                dto.name(),
                coordinateMapper.fromDTO(dto.coordinates()),
                dto.creationDate(),
                dto.studentsCount(),
                dto.expelledStudents(),
                dto.formOfEducation(),
                dto.semesterEnum(),
                personMapper.fromDTO(dto.groupAdmin())
        );
    }
}
