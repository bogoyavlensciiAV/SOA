package jpchs.spring_app.controller;

import jpchs.spring_app.dto.paging.PagedRequest;
import jpchs.spring_app.dto.paging.PageResponse;
import jpchs.spring_app.dto.StudyGroupDTO;
import jpchs.spring_app.service.StudyGroupService;
import jpchs.spring_app.util.PagedRequestHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class StudyGroupController {

    private final StudyGroupService studyGroupService;
    private final PagedRequestHelper pagedRequestHelper;

    @GetMapping
    public PageResponse<StudyGroupDTO> getStudyGroups(PagedRequest req) {
        var validatedRequest = pagedRequestHelper.convertAndValidate(req);
        return studyGroupService.findAll(
                validatedRequest.filter() == null ? List.of() : validatedRequest.filter(),
                validatedRequest.sort() == null ? List.of() : validatedRequest.sort(),
                validatedRequest.page() == null ? 0 : validatedRequest.page(),
                validatedRequest.pageSize() == null ? 25 : validatedRequest.pageSize()
        );
    }
}
