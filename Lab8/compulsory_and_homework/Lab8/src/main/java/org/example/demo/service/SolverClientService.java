package org.example.demo.service;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SolverClientService {

    private final RestTemplate restTemplate;
    private final String BASE_SOLVER_URL = "http://localhost:8083/api/solver/solve";

    public SolverClientService() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(1000);
        factory.setReadTimeout(1000);
        this.restTemplate = new RestTemplate(factory);
    }

    @Retry(name = "solverRetry", fallbackMethod = "solveFallback")
    public String invokeStableMatch(Object payload, String algorithm) {

        String finalUrl = BASE_SOLVER_URL + "?algorithm=" + algorithm;

        System.out.println("--- Calling StableMatch Service with strategy: " + algorithm + " ---");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(payload, headers);

        return restTemplate.postForObject(finalUrl, requestEntity, String.class);
    }

    public String solveFallback(Object payload, String algorithm, Throwable t) {
        System.out.println("!!! StableMatch Failed. Fallback executed. Error: " + t.getMessage());
        return "{ \"assignments\": [], \"unassignedStudents\": [\"ALL_STUDENTS_FALLBACK\"] }";
    }
}