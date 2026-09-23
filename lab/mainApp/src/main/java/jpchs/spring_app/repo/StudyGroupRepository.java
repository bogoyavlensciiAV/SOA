package jpchs.spring_app.repo;

import jpchs.spring_app.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer>, JpaSpecificationExecutor<StudyGroup> {

    @NativeQuery("""
        SELECT COALESCE(SUM(s.expelled_students), 0)
        FROM study_group s
    """)
    int countExpelledStudents();

    @NativeQuery("""
        SELECT COUNT(*)
        FROM study_group s
        WHERE s.students_count = :studentsCount
    """)
    int countGroupsWithStudentsCount(@Param("studentsCount") int studentsCount);

    @NativeQuery("""
        SELECT *
        FROM study_group s
        WHERE s.name LIKE CONCAT(:prefix, '%')
    """)
    List<StudyGroup> findAllNameStartingWithPrefix(@Param("prefix") String prefix);
}
