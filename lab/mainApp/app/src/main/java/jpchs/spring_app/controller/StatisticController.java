package jpchs.spring_app.controller;

import jakarta.transaction.Transactional;
import jpchs.StatisticsBean;
import jpchs.dto.CountResponse;
import jpchs.dto.StudyGroupDTO;
import jpchs.dto.SumResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Transactional
@RequestMapping("/study-groups")
public class StatisticController {

    private final StatisticsBean statisticsService;

    @GetMapping("/expelled-students/sum")
    public SumResponse calculateSum() {
        return statisticsService.calculateSum();
    }

    @GetMapping("/students-count/{student-count}/count")
    public CountResponse countGroupWithStudentCount(
            @PathVariable("student-count") int studentCount
    ) {
        return statisticsService.countGroupWithStudentsCount(studentCount);
    }

    @GetMapping("/name/starts-with/{prefix}")
    public List<StudyGroupDTO> findAllStartsWithPrefix(
            @PathVariable("prefix") String prefix
    ) {
        return statisticsService.findAllStartsWithPrefix(prefix);
    }
}
