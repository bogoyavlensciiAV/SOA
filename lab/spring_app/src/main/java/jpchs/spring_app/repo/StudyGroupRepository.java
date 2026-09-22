package jpchs.spring_app.repo;

import jpchs.spring_app.entity.StudyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StudyGroupRepository extends JpaRepository<StudyGroup, Integer>, JpaSpecificationExecutor<StudyGroup> {

}
