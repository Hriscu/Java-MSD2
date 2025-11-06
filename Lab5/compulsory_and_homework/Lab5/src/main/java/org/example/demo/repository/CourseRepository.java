package org.example.demo.repository;

import org.example.demo.entity.Course;
import org.example.demo.entity.Course.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByCode(String code);

    @Query("select c from Course c where c.type = :type and c.instructor.email = :email")
    List<Course> findByTypeAndInstructorEmail(CourseType type, String email);

    @Modifying
    @Transactional
    @Query("update Course c set c.description = :description where c.code = :code")
    int updateDescriptionByCode(String code, String description);
}
