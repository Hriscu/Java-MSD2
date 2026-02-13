package org.example.stablematch.service;

import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.example.stablematch.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MatchingServiceTest {

    private MatchingService matchingService;

    @BeforeEach
    void setUp() {
        // TRICK: Use a real SimpleMeterRegistry for tests to avoid constructor errors.
        // We do not need @Mock for this; it is cleaner this way.
        matchingService = new MatchingService(new SimpleMeterRegistry());
    }

    // --- HAPPY PATH SCENARIO ---
    @Test
    void solveGreedy_ShouldReturnMatches_WhenInputIsValid() {
        // 1. Arrange (Prepare test data - Student1 wants Course1, Course1 has capacity)
        StudentInput s1 = new StudentInput();
        s1.setId("Student1");
        s1.setPreferences(Collections.singletonList("Course1"));

        CourseInput c1 = new CourseInput();
        c1.setId("Course1");
        c1.setCapacity(1);

        MatchRequest request = new MatchRequest();
        request.setStudents(Collections.singletonList(s1));
        request.setCourses(Collections.singletonList(c1));

        // 2. Act (Execute the method)
        MatchResponse response = matchingService.solveGreedy(request);

        // 3. Assert (Verify the results)
        assertNotNull(response, "Response should not be null");

        assertEquals(1, response.getAssignments().size(), "There should be exactly 1 match");

        // Check if the correct student was matched
        assertEquals("Student1", response.getAssignments().get(0).getStudentId());
    }

    // --- ERROR SCENARIO ---
    @Test
    void solveGreedy_ShouldThrowException_WhenInputIsNull() {
        // 2. Act & Assert
        // We expect a NullPointerException because the method tries to access request.getCourses()
        assertThrows(NullPointerException.class, () -> {
            matchingService.solveGreedy(null);
        });
    }
}