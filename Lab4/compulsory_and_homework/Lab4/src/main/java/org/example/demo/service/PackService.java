package org.example.demo.service;

import org.example.demo.entity.Pack;
import org.example.demo.repository.PackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackService {
    private final PackRepository repo;
    public PackService(PackRepository repo){ this.repo = repo; }

    public Pack save(Pack p){ return repo.save(p); }
    public List<Pack> findByYearAndSemester(int y, int s){ return repo.findByYearAndSemester(y, s); }
    public List<Pack> findPacksForYear(int y){ return repo.findPacksForYear(y); }
}
