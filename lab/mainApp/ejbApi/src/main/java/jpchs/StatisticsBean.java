package jpchs;

import jakarta.ejb.Remote;
import jpchs.dto.CountResponse;
import jpchs.dto.StudyGroupDTO;
import jpchs.dto.SumResponse;

import java.util.List;

@Remote
public interface StatisticsBean {

    SumResponse calculateSum();

    CountResponse countGroupWithStudentsCount(int studentsCount);

    List<StudyGroupDTO> findAllStartsWithPrefix(String prefix);
}
