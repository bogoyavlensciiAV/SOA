package jpchs.spring_app.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "person")
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @Positive
    @Column(nullable = false)
    private int weight;

    @Size(min = 6)
    @Column(name = "passport_id", unique = true)
    private String passportID;

    @Valid
    @Embedded
    private Location location;
}