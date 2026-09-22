package jpchs.spring_app.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jpchs.spring_app.enm.FormOfEducation;
import jpchs.spring_app.enm.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "study_group")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;

    @NotBlank
    @Column(nullable = false)
    String name;

    @Valid
    @NotNull
    @Embedded
    Coordinates coordinates;

    @Column(name = "creation_date", nullable = false, updatable = false)
    Date creationDate;

    @Positive
    @Column(name = "students_count")
    int studentsCount;

    @Positive
    @Column(name = "expelled_students")
    Integer expelledStudents;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "form_of_education", nullable = false)
    FormOfEducation formOfEducation;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    Semester semesterEnum;

    @Valid
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "group_admin_id")
    Person groupAdmin;

    @PrePersist
    protected void onCreate() {
        this.creationDate = new Date();
    }
}