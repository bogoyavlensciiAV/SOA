package jpchs.spring_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Embeddable
@Data
public class Location {

    @NotNull
    @Column(name = "location_x")
    Long x;

    @NotNull
    @Column(name = "location_y")
    Long y;

    @Size(min = 1)
    @Column(name = "location_name")
    String name;
}
