package jpchs.spring_app.dto.inner;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LocationDTO(
        @NotNull
        Long x,

        @NotNull
        Long y,

        @Size(min = 1)
        String name
) {
}
