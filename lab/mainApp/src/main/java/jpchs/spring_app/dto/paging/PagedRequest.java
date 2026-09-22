package jpchs.spring_app.dto.paging;

import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record PagedRequest (
        List<String> filter,
        List<String> sort,
        @PositiveOrZero(message = "Should be greater or equal to 0")
        Integer page,
        @PositiveOrZero(message = "Should be greater or equal to 0")
        Integer pageSize
) {
}
