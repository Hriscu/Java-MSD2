package org.example.demo.repository;
import org.example.demo.entity.InstructorPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InstructorPreferenceRepository extends JpaRepository<InstructorPreference, Long> {
    List<InstructorPreference> findByOptionalCourseId(Long courseId);
}