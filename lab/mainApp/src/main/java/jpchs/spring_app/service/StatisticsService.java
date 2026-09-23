package jpchs.spring_app.service;

import jakarta.transaction.Transactional;
import jpchs.spring_app.dto.CountResponse;
import jpchs.spring_app.dto.StudyGroupDTO;
import jpchs.spring_app.dto.SumResponse;
import jpchs.spring_app.mapper.StudyGroupMapper;
import jpchs.spring_app.repo.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupMapper mapper;

    public SumResponse calculateSum() {
        return new SumResponse(studyGroupRepository.countExpelledStudents());
    }

    public CountResponse countGroupWithStudentsCount(int studentsCount) {
        return new CountResponse(studyGroupRepository.countGroupsWithStudentsCount(studentsCount));
    }

    public List<StudyGroupDTO> findAllStartsWithPrefix(String prefix) {
        return studyGroupRepository.findAllNameStartingWithPrefix(prefix)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}
