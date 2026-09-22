package jpchs.spring_app.dto.paging;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int page,
        int pageSize,
        long total,
        long totalPages
) {
}
