package org.example.demo.repository;

import org.example.demo.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudentId(Long studentId);
    boolean existsByStudentIdAndPackId(Long studentId, Long packId);
    @Query("select e.course.name from Enrollment e where e.student.id = :studentId")
    List<String> findCourseNamesByStudent(Long studentId);

    @Modifying
    @Transactional
    @Query("delete from Enrollment e where e.student.id = :studentId")
    int deleteByStudentId(Long studentId);

    Optional<Enrollment> findByStudentIdAndPackId(Long studentId, Long packId);
}
