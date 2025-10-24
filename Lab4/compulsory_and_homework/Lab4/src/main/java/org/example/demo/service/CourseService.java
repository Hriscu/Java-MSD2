package org.example.demo.service;

import org.example.demo.entity.Course;
import org.example.demo.entity.Course.CourseType;
import org.example.demo.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository repo;
    public CourseService(CourseRepository repo){ this.repo = repo; }

    public Course create(Course c){ return repo.save(c); }
    public Optional<Course> findByCode(String code){ return repo.findByCode(code); }
    public List<Course> byTypeAndInstructorEmail(CourseType type, String email){
        return repo.findByTypeAndInstructorEmail(type, email);
    }
    public int updateDescriptionByCode(String code, String desc){
        return repo.updateDescriptionByCode(code, desc);
    }
    public void delete(Long id){ repo.deleteById(id); }
    public List<Course> all(){ return repo.findAll(); }
}
