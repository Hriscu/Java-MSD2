package org.example.demo.controller;

import org.example.demo.service.SolverClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ResilienceTestController {

    private final SolverClientService solverClient;

    public ResilienceTestController(SolverClientService solverClient) {
        this.solverClient = solverClient;
    }

    @GetMapping("/api/test-retry")
    public String triggerRetryMechanism() {
        Map<String, Object> validDummyData = new HashMap<>();
        validDummyData.put("students", new ArrayList<>());
        validDummyData.put("courses", new ArrayList<>());

        return solverClient.invokeStableMatch(validDummyData, "random");
    }
}