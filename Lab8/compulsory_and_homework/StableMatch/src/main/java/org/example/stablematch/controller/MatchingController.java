package org.example.stablematch.controller;

import org.example.stablematch.dto.*;
import org.example.stablematch.service.MatchingService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/solver")
public class MatchingController {

    private final MatchingService service;

    private MatchResponse lastSolution;

    public MatchingController(MatchingService service) {
        this.service = service;
    }

    @PostMapping("/solve")
    public MatchResponse solveProblem(@RequestBody MatchRequest request,
                                      @RequestParam(defaultValue = "greedy") String algorithm) {
        if ("random".equalsIgnoreCase(algorithm)) {
            this.lastSolution = service.solveRandom(request);
        } else {
            this.lastSolution = service.solveGreedy(request);
        }
        return this.lastSolution;
    }

    @GetMapping("/assignments")
    public MatchResponse getLastSolution() {
        return this.lastSolution;
    }

    @GetMapping("/assignments/{studentId}")
    public MatchPair getForStudent(@PathVariable String studentId) {
        if (lastSolution == null) return null;
        return lastSolution.getAssignments().stream()
                .filter(a -> a.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }
}