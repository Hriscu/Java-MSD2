package org.example.demo.controller;

import org.example.demo.entity.*;
import org.example.demo.repository.*;
import org.example.demo.service.SolverClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/integration")
public class MatchingIntegrationController {

    private final PackRepository packRepo;
    private final StudentRepository studentRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final SolverClientService solverClient;

    public MatchingIntegrationController(PackRepository packRepo,
                                         StudentRepository studentRepo,
                                         EnrollmentRepository enrollmentRepo,
                                         SolverClientService solverClient) {
        this.packRepo = packRepo;
        this.studentRepo = studentRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.solverClient = solverClient;
    }

    @PostMapping("/solve-pack/{packId}")
    public ResponseEntity<?> solveForPack(@PathVariable Long packId) {

        Pack pack = packRepo.findById(packId)
                .orElseThrow(() -> new RuntimeException("Pack not found"));

        Map<String, Object> request = new HashMap<>();

        List<String> courseCodesInPack = new ArrayList<>();

        List<Map<String, Object>> coursesJson = pack.getCourses().stream().map(c -> {
            courseCodesInPack.add(c.getCode());

            Map<String, Object> m = new HashMap<>();
            m.put("id", c.getCode());
            m.put("capacity", 5);
            return m;
        }).collect(Collectors.toList());

        List<Student> dbStudents = studentRepo.findAll();
        List<Map<String, Object>> studentsJson = new ArrayList<>();

        for (Student s : dbStudents) {
            Map<String, Object> sMap = new HashMap<>();
            sMap.put("id", s.getCode());

            sMap.put("preferences", new ArrayList<>(courseCodesInPack));

            studentsJson.add(sMap);
        }

        request.put("courses", coursesJson);
        request.put("students", studentsJson);

        System.out.println("Sending to Solver: " + studentsJson.size() + " students and " + coursesJson.size() + " courses.");

        String jsonResult = solverClient.invokeStableMatch(request);

        return ResponseEntity.ok("Solver response: " + jsonResult);
    }
}