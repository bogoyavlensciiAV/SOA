package jpchs.spring_app.dto.paging;

import org.springframework.data.domain.Sort;

import java.util.List;

public record PagedRequestValidated(
        List<Filter> filter,
        List<Sort> sort,
        Integer page,
        Integer pageSize
) {
}
