package org.example.demo.controller;

import org.example.demo.entity.Grade;
import org.example.demo.repository.GradeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeRepository gradeRepo;

    public GradeController(GradeRepository gradeRepo) {
        this.gradeRepo = gradeRepo;
    }

    @GetMapping
    public List<Grade> getAllGrades() {
        return gradeRepo.findAll();
    }

    @PostMapping("/upload-csv")
    public ResponseEntity<?> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3) {
                    try {
                        String student = data[0].trim();
                        String course = data[1].trim();
                        Double val = Double.parseDouble(data[2].trim());
                        gradeRepo.save(new Grade(student, course, val));
                        count++;
                    } catch (NumberFormatException e) {
                    }
                }
            }
            return ResponseEntity.ok("CSV processed. " + count + " grades saved.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error processing CSV: " + e.getMessage());
        }
    }
}