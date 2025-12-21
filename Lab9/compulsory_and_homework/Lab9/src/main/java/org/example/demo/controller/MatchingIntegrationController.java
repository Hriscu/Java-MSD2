package org.example.demo.controller;

import org.example.demo.entity.*;
import org.example.demo.repository.*;
import org.example.demo.service.SolverClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/integration")
public class MatchingIntegrationController {

    private final PackRepository packRepo;
    private final StudentRepository studentRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final SolverClientService solverClient;
    private final GradeRepository gradeRepo;
    private final InstructorPreferenceRepository prefRepo;

    public MatchingIntegrationController(PackRepository packRepo,
                                         StudentRepository studentRepo,
                                         EnrollmentRepository enrollmentRepo,
                                         SolverClientService solverClient,
                                         GradeRepository gradeRepo,
                                         InstructorPreferenceRepository prefRepo) {
        this.packRepo = packRepo;
        this.studentRepo = studentRepo;
        this.enrollmentRepo = enrollmentRepo;
        this.solverClient = solverClient;
        this.gradeRepo = gradeRepo;
        this.prefRepo = prefRepo;
    }

    @PostMapping("/solve-pack/{packId}")
    public ResponseEntity<?> solveForPack(@PathVariable Long packId,
                                          @RequestParam(defaultValue = "random") String strategy) {
        try {
            Pack pack = packRepo.findById(packId)
                    .orElseThrow(() -> new RuntimeException("Pack not found"));

            String result = solvePackInternal(pack, strategy);
            return ResponseEntity.ok("Solver response (" + strategy + "): " + result);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/solve-all-packs")
    public ResponseEntity<?> solveAllPacks(@RequestParam(defaultValue = "random") String strategy) {

        List<Pack> allPacks = packRepo.findAll();
        Map<String, Object> finalResults = new LinkedHashMap<>();

        ObjectMapper mapper = new ObjectMapper();

        if (allPacks.isEmpty()) {
            return ResponseEntity.ok("No packs found in database.");
        }

        System.out.println(">>> SOLVING ALL PACKS (" + allPacks.size() + ") with strategy: " + strategy);

        for (Pack pack : allPacks) {
            try {
                String rawJsonSolution = solvePackInternal(pack, strategy);

                Object jsonObject = mapper.readValue(rawJsonSolution, Object.class);

                finalResults.put("Pack: " + pack.getName(), jsonObject);

            } catch (Exception e) {
                finalResults.put("Pack ID " + pack.getId(), "Error: " + e.getMessage());
            }
        }

        return ResponseEntity.ok(finalResults);
    }

    private String solvePackInternal(Pack pack, String strategy) {

        if (pack.getCourses().isEmpty()) {
            throw new RuntimeException("Pack has no courses!");
        }

        List<String> courseCodesInPack = new ArrayList<>();
        List<Map<String, Object>> coursesJson = pack.getCourses().stream().map(c -> {
            courseCodesInPack.add(c.getCode());
            Map<String, Object> m = new HashMap<>();
            m.put("id", c.getCode());
            m.put("capacity", 5);
            return m;
        }).collect(Collectors.toList());
        List<Student> dbStudents = studentRepo.findAll();
        String solverAlgorithm;

        if ("merit".equalsIgnoreCase(strategy)) {
            Course referenceCourse = pack.getCourses().get(0);
            dbStudents.sort((s1, s2) -> {
                double score1 = calculateStudentScore(s1, referenceCourse);
                double score2 = calculateStudentScore(s2, referenceCourse);
                return Double.compare(score2, score1);
            });
            solverAlgorithm = "greedy";
        } else {
            solverAlgorithm = "random";
        }

        List<Map<String, Object>> studentsJson = new ArrayList<>();
        for (Student s : dbStudents) {
            Map<String, Object> sMap = new HashMap<>();
            sMap.put("id", s.getCode());
            sMap.put("preferences", new ArrayList<>(courseCodesInPack));
            studentsJson.add(sMap);
        }

        Map<String, Object> request = new HashMap<>();
        request.put("courses", coursesJson);
        request.put("students", studentsJson);

        System.out.println("   -> Invoking Solver for pack: " + pack.getName());
        return solverClient.invokeStableMatch(request, solverAlgorithm);
    }

    private double calculateStudentScore(Student student, Course optionalCourse) {
        List<InstructorPreference> prefs = prefRepo.findByOptionalCourse(optionalCourse);
        if (prefs.isEmpty()) return 0.0;

        double totalScore = 0.0;
        for (InstructorPreference pref : prefs) {
            String compulsoryAbbr = pref.getCompulsoryCourseAbbr();
            Double weight = pref.getWeight();
            Optional<Grade> grade = gradeRepo.findByStudentCodeAndCourseCode(student.getCode(), compulsoryAbbr);

            if (grade.isPresent()) {
                totalScore += grade.get().getValue() * weight;
            }
        }
        return totalScore;
    }
}