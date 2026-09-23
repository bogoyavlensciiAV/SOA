package jpchs.spring_app.service;

import jakarta.transaction.Transactional;
import jpchs.spring_app.dto.StudyGroupRequest;
import jpchs.spring_app.dto.inner.ErrorItem;
import jpchs.spring_app.dto.paging.PageResponse;
import jpchs.spring_app.dto.StudyGroupDTO;
import jpchs.spring_app.dto.paging.Filter;
import jpchs.spring_app.enm.Errors;
import jpchs.spring_app.exception.ApplicationException;
import jpchs.spring_app.mapper.StudyGroupMapper;
import jpchs.spring_app.repo.StudyGroupRepository;
import jpchs.spring_app.repo.spec.StudyGroupSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final StudyGroupSpecification studyGroupSpecification;
    private final StudyGroupMapper mapper;

    public PageResponse<StudyGroupDTO> findAll(List<Filter> filters, List<Sort> sorts, int page, int pageSize) {
        var sort = sorts.stream().reduce(Sort::and).orElse(Sort.unsorted());
        var spec = studyGroupSpecification.from(filters);
        var pageable = PageRequest.of(page, pageSize, sort);
        var studyGroupPage = studyGroupRepository.findAll(spec, pageable);
        return new PageResponse<>(
                studyGroupPage.map(mapper::toDTO).toList(),
                page,
                pageSize,
                studyGroupPage.getTotalElements(),
                studyGroupPage.getTotalPages()
        );
    }

    public StudyGroupDTO findGroupById(Integer id) {
        return studyGroupRepository.findById(id).map(mapper::toDTO).orElseThrow(() -> new ApplicationException(
            List.of(new ErrorItem(Errors.NOT_FOUND, "StudyGroup not found for id: " + id)),
                HttpStatus.NOT_FOUND
        ));
    }

    public StudyGroupDTO updateStudyGroup(Integer id, StudyGroupRequest req) {
        studyGroupRepository.findById(id).orElseThrow(() -> new ApplicationException(
                List.of(new ErrorItem(Errors.NOT_FOUND, "StudyGroup not found for id: " + id)),
                HttpStatus.NOT_FOUND
        ));

        var entityGroup = studyGroupRepository.save(mapper.fromRequest(req));
        return mapper.toDTO(entityGroup);
    }

    public StudyGroupDTO create(StudyGroupRequest req) {
        return mapper.toDTO(studyGroupRepository.save(mapper.fromRequest(req)));
    }

    public void delete(Integer id) {
        var entity = studyGroupRepository.findById(id).orElseThrow(() -> new ApplicationException(
                List.of(new ErrorItem(Errors.NOT_FOUND, "StudyGroup not found for id: " + id)),
                HttpStatus.NOT_FOUND
        ));

        studyGroupRepository.delete(entity);
    }
}
