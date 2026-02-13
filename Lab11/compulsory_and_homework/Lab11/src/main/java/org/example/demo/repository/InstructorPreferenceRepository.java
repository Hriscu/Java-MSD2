package org.example.demo.repository;

import org.example.demo.entity.Course;
import org.example.demo.entity.InstructorPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorPreferenceRepository extends JpaRepository<InstructorPreference, Long> {
    List<InstructorPreference> findByOptionalCourse(Course optionalCourse);
}