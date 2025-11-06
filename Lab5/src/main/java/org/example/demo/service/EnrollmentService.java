package org.example.demo.service;

import org.example.demo.entity.Enrollment;
import org.example.demo.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentService {
    private final EnrollmentRepository repo;
    public EnrollmentService(EnrollmentRepository repo){ this.repo = repo; }
    public Enrollment save(Enrollment e){ return repo.save(e); }
}
