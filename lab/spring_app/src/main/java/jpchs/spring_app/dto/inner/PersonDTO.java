package jpchs.spring_app.dto.inner;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jpchs.spring_app.entity.Location;

public record PersonDTO(
        @NotBlank
        String name,

        @Positive
        int weight,

        @Size(min = 6)
        String passportID,

        Location location
) {
}
