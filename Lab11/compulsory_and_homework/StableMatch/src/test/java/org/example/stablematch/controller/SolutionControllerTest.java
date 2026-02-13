package org.example.stablematch.controller;

import org.example.stablematch.dto.MatchRequest;
import org.example.stablematch.dto.MatchResponse;
import org.example.stablematch.service.MatchingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchingController.class) // Targeting the correct Controller class
class MatchingControllerTest {

    @Autowired
    private MockMvc mockMvc; // Tool to simulate HTTP requests

    @MockBean
    private MatchingService matchingService; // We mock the service logic

    @Test
    void solve_ShouldReturn200_WhenInputIsValid() throws Exception {
        // 1. Arrange: Define the mock behavior
        // We simulate that the service returns an empty response
        MatchResponse dummyResponse = new MatchResponse(new ArrayList<>(), new ArrayList<>());

        // NOTE: The controller uses 'greedy' by default, so it calls solveGreedy
        when(matchingService.solveGreedy(any(MatchRequest.class))).thenReturn(dummyResponse);

        // 2. Act & Assert: Perform the POST request
        // FIX: The URL is now correct: "/api/solver" (class) + "/solve" (method)
        mockMvc.perform(post("/api/solver/solve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"students\": [], \"courses\": []}")) // JSON payload
                .andExpect(status().isOk()); // We expect HTTP 200 OK
    }
}