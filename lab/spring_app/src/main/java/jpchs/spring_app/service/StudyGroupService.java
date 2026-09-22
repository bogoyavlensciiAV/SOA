package jpchs.spring_app.service;

import jpchs.spring_app.dto.StudyGroupDTO;
import jpchs.spring_app.repo.StudyGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;

    public List<StudyGroupDTO> findAll() {
        return List.of();
    }
}
