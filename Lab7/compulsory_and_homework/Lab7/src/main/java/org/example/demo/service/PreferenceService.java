package org.example.demo.service;

import org.example.demo.entity.*;
import org.example.demo.exception.ResourceNotFoundException;
import org.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PreferenceService {

    private final PreferenceRepository prefRepo;
    private final StudentRepository studentRepo;
    private final PackRepository packRepo;
    private final CourseRepository courseRepo;

    public PreferenceService(PreferenceRepository prefRepo,
                             StudentRepository studentRepo,
                             PackRepository packRepo,
                             CourseRepository courseRepo) {
        this.prefRepo = prefRepo;
        this.studentRepo = studentRepo;
        this.packRepo = packRepo;
        this.courseRepo = courseRepo;
    }

    public List<Preference> getPreferences(Long studentId, Long packId){
        return prefRepo.findByStudentIdAndPackIdOrderByRankAsc(studentId, packId);
    }

    @Transactional
    public Preference saveOrUpdate(Long studentId, Long packId, Long courseId, Integer rank){
        Student s = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        Pack p = packRepo.findById(packId)
                .orElseThrow(() -> new ResourceNotFoundException("Pack not found"));

        Course c = courseRepo.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        Optional<Preference> existing =
                prefRepo.findByStudentIdAndPackIdAndCourseId(studentId, packId, courseId);

        Preference pref = existing.orElse(new Preference(s, p, c, rank));
        pref.setRank(rank);

        return prefRepo.save(pref);
    }

    @Transactional
    public void deleteAllForPack(Long studentId, Long packId){
        prefRepo.deleteByStudentIdAndPackId(studentId, packId);
    }
}
