package org.example.demo.service;

import org.example.demo.entity.Student;
import org.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repo;
    public StudentService(StudentRepository repo) { this.repo = repo; }

    public Student save(Student s){ return repo.save(s); }
    public List<Student> findAll(){ return repo.findAll(); }
    public List<Student> findByYear(int year){ return repo.findByYear(year); }
}
