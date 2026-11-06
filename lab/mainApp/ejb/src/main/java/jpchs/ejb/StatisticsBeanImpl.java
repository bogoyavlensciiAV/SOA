package jpchs.ejb;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jpchs.StatisticsBean;
import jpchs.dto.CountResponse;
import jpchs.dto.StudyGroupDTO;
import jpchs.dto.SumResponse;
import jpchs.mapper.StudyGroupMapper;
import jpchs.repo.StudyGroupRepository;

import java.util.List;

@Stateless
public class StatisticsBeanImpl implements StatisticsBean {

    @Inject
    private StudyGroupRepository studyGroupRepository;

    @Inject
    private StudyGroupMapper mapper;

    @Override
    public SumResponse calculateSum() {
        return new SumResponse(studyGroupRepository.countExpelledStudents());
    }

    @Override
    public CountResponse countGroupWithStudentsCount(int studentsCount) {
        return new CountResponse(studyGroupRepository.countGroupsWithStudentsCount(studentsCount));
    }

    @Override
    public List<StudyGroupDTO> findAllStartsWithPrefix(String prefix) {
        return studyGroupRepository.findAllNameStartingWithPrefix(prefix)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}