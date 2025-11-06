package org.example.demo.service;

import org.example.demo.entity.Student;
import org.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository repo;

    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    public Student save(Student s){
        return repo.save(s);
    }

    public List<Student> findAll(){
        return repo.findAll();
    }

    public List<Student> findByYear(int year){
        return repo.findByYear(year);
    }

    public Optional<Student> findById(Long id) {
        return repo.findById(id);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
