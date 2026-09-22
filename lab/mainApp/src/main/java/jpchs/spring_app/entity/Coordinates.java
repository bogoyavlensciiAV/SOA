package jpchs.spring_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coordinates {

    @NotNull
    @Max(726)
    @Column(name = "coord_x", nullable = false)
    Long x;

    @NotNull
    @Max(189)
    @Column(name = "coord_y", nullable = false)
    Integer y;
}
