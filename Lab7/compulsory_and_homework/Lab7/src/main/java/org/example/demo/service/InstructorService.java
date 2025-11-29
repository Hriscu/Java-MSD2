package org.example.demo.service;

import org.example.demo.entity.Instructor;
import org.example.demo.repository.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorService {
    private final InstructorRepository repo;
    public InstructorService(InstructorRepository repo){ this.repo = repo; }

    public Instructor save(Instructor i){ return repo.save(i); }
    public Optional<Instructor> findByEmail(String email){ return repo.findByEmail(email); }
}
