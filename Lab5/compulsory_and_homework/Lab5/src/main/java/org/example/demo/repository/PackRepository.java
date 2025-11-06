package org.example.demo.repository;

import org.example.demo.entity.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PackRepository extends JpaRepository<Pack, Long> {
    List<Pack> findByYearAndSemester(int year, int semester);

    @Query("select p from Pack p where p.year = :year")
    List<Pack> findPacksForYear(int year);
}
