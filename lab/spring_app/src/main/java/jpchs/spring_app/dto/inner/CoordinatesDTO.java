package jpchs.spring_app.dto.inner;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

public record CoordinatesDTO(
    @NotNull @Max(726)
    Long x,

    @NotNull @Max(189)
    Integer y
) {
}
