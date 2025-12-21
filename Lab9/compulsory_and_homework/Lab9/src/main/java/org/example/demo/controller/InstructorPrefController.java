package org.example.demo.controller;

import org.example.demo.entity.Course;
import org.example.demo.entity.InstructorPreference;
import org.example.demo.repository.CourseRepository;
import org.example.demo.repository.InstructorPreferenceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor-prefs")
public class InstructorPrefController {

    private final InstructorPreferenceRepository prefRepo;
    private final CourseRepository courseRepo;

    public InstructorPrefController(InstructorPreferenceRepository prefRepo, CourseRepository courseRepo) {
        this.prefRepo = prefRepo;
        this.courseRepo = courseRepo;
    }

    @PostMapping("/{courseCode}")
    public ResponseEntity<?> addPreference(@PathVariable String courseCode,
                                           @RequestParam String compulsoryAbbr,
                                           @RequestParam Double weight) {
        Course c = courseRepo.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        InstructorPreference ip = new InstructorPreference(c, compulsoryAbbr, weight);
        prefRepo.save(ip);
        return ResponseEntity.ok("Preference added");
    }

    @GetMapping("/{courseCode}")
    public List<InstructorPreference> get(@PathVariable String courseCode) {
        Course c = courseRepo.findByCode(courseCode)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return prefRepo.findByOptionalCourse(c);
    }
}