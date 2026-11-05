package jpchs.dto.paging;

import java.util.List;

public record PagedRequestValidated(
        List<Filter> filter,
        List<SortOrder> sort,
        Integer page,
        Integer pageSize
) {
}
