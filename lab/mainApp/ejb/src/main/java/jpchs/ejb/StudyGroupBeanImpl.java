package jpchs.ejb;


import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jpchs.StudyGroupBean;
import jpchs.dto.StudyGroupDTO;
import jpchs.dto.StudyGroupRequest;
import jpchs.dto.inner.ErrorItem;
import jpchs.dto.paging.Filter;
import jpchs.dto.paging.PageResponse;
import jpchs.dto.paging.SortOrder;
import jpchs.enm.Errors;
import jpchs.entity.StudyGroup;
import jpchs.exception.ApplicationException;
import jpchs.mapper.StudyGroupMapper;
import jpchs.repo.StudyGroupRepository;

import java.util.List;

import static java.util.Objects.nonNull;

@Stateless
public class StudyGroupBeanImpl implements StudyGroupBean {

    private static final int NOT_FOUND = 404;

    @Inject
    private StudyGroupRepository studyGroupRepository;

    @Inject
    private StudyGroupMapper mapper;

    @Override
    public PageResponse<StudyGroupDTO> findAll(List<Filter> filters, List<SortOrder> sorts, int page, int pageSize) {
        var result = studyGroupRepository.findAll(filters, sorts, page, pageSize);
        return new PageResponse<>(
                result.content().stream().map(mapper::toDTO).toList(),
                page,
                pageSize,
                result.total(),
                result.totalPages(pageSize)
        );
    }

    @Override
    public StudyGroupDTO findGroupById(Integer id) {
        return mapper.toDTO(getOrThrow(id));
    }

    @Override
    public StudyGroupDTO updateStudyGroup(Integer id, StudyGroupRequest req) {
        var entity = getOrThrow(id);

        var update = mapper.fromRequest(req);
        update.setId(entity.getId());
        update.setCreationDate(entity.getCreationDate());
        if (nonNull(update.getGroupAdmin()) && nonNull(entity.getGroupAdmin())) {
            update.getGroupAdmin().setId(entity.getGroupAdmin().getId());
        }
        return mapper.toDTO(studyGroupRepository.save(update));
    }

    @Override
    public StudyGroupDTO create(StudyGroupRequest req) {
        return mapper.toDTO(studyGroupRepository.save(mapper.fromRequest(req)));
    }

    @Override
    public void delete(Integer id) {
        studyGroupRepository.delete(getOrThrow(id));
    }

    private StudyGroup getOrThrow(Integer id) {
        return studyGroupRepository.findById(id).orElseThrow(() -> new ApplicationException(
                List.of(new ErrorItem(Errors.NOT_FOUND, "StudyGroup not found for id: " + id)),
                NOT_FOUND
        ));
    }
}
