package jpchs.spring_app.service;

import jpchs.spring_app.dto.paging.PageResponse;
import jpchs.spring_app.dto.StudyGroupDTO;
import jpchs.spring_app.dto.paging.Filter;
import jpchs.spring_app.mapper.StudyGroupMapper;
import jpchs.spring_app.repo.StudyGroupRepository;
import jpchs.spring_app.repo.spec.StudyGroupSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupSpecification studyGroupSpecification;
    private final StudyGroupMapper mapper;

    public PageResponse<StudyGroupDTO> findAll(List<Filter> filters, List<Sort> sorts, int page, int pageSize) {
        var sort = sorts.stream().reduce(Sort::and).orElse(Sort.unsorted());
        var spec = studyGroupSpecification.from(filters);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        var studyGroupPage = studyGroupRepository.findAll(spec, pageable);
        return new PageResponse<>(
                studyGroupPage.map(mapper::toDTO).toList(),
                page,
                pageSize,
                studyGroupPage.getTotalElements(),
                studyGroupPage.getTotalPages()
        );
    }
}
