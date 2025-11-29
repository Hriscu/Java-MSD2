package org.example.demo.repository;

import org.example.demo.entity.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreferenceRepository extends JpaRepository<Preference, Long> {
    List<Preference> findByStudentIdAndPackIdOrderByRankAsc(Long studentId, Long packId);
    Optional<Preference> findByStudentIdAndPackIdAndCourseId(Long studentId, Long packId, Long courseId);
    void deleteByStudentIdAndPackId(Long studentId, Long packId);
}
