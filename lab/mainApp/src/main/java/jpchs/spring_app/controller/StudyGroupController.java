package jpchs.spring_app.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jpchs.spring_app.dto.StudyGroupDTO;
import jpchs.spring_app.dto.StudyGroupRequest;
import jpchs.spring_app.dto.paging.PageResponse;
import jpchs.spring_app.dto.paging.PagedRequest;
import jpchs.spring_app.service.StudyGroupService;
import jpchs.spring_app.util.PagedRequestHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("study-groups")
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

    @GetMapping("{id}")
    public StudyGroupDTO getStudyGroupById(
            @PathVariable("id") @Positive(message = "Id should be positive") Integer id
    ) {
        return studyGroupService.findGroupById(id);
    }

    @PutMapping("{id}")
    public StudyGroupDTO updateStudyGroup(
            @PathVariable("id") @Positive(message = "Id should be positive") Integer id,
            @RequestBody @Valid StudyGroupRequest req
    ) {
        return studyGroupService.updateStudyGroup(id, req);
    }

    @PostMapping
    public ResponseEntity<StudyGroupDTO> create(
            @RequestBody @Valid StudyGroupRequest req
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studyGroupService.create(req));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") @Positive(message = "Id should be positive") Integer id
    ) {
        studyGroupService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}