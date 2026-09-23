package jpchs.spring_app.dto.inner;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PersonDTO(
        @NotBlank
        String name,

        @Positive
        int weight,

        @Size(min = 6)
        String passportID,

        @Valid
        LocationDTO location
) {
}
