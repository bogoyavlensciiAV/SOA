package jpchs;

import jakarta.ejb.Remote;
import jpchs.dto.StudyGroupDTO;
import jpchs.dto.StudyGroupRequest;
import jpchs.dto.paging.Filter;
import jpchs.dto.paging.PageResponse;
import jpchs.dto.paging.SortOrder;

import java.util.List;

@Remote
public interface StudyGroupBean {
    PageResponse<StudyGroupDTO> findAll(List<Filter> filters, List<SortOrder> sorts, int page, int pageSize);
    StudyGroupDTO findGroupById(Integer id);
    StudyGroupDTO updateStudyGroup(Integer id, StudyGroupRequest req);
    StudyGroupDTO create(StudyGroupRequest req);
    void delete(Integer id);
}
