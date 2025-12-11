package org.example.demo.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SolverClientService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String SOLVER_URL = "http://localhost:8083/api/solver/solve";

    @Retry(name = "solverRetry", fallbackMethod = "solveFallback")
    public String invokeStableMatch(Object payload) {
        System.out.println("--- Calling StableMatch Service ---");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(payload, headers);

        return restTemplate.postForObject(SOLVER_URL, requestEntity, String.class);
    }

    public String solveFallback(Object payload, Throwable t) {
        System.out.println("!!! StableMatch is DOWN or Error. Executing Fallback logic. Error: " + t.getMessage());
        return "{ \"assignments\": [], \"unassignedStudents\": [\"ALL_STUDENTS_FALLBACK\"] }";
    }
}