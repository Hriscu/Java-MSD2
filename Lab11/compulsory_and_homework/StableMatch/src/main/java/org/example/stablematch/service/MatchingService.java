package org.example.stablematch.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.example.stablematch.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchingService {

    private static final Logger logger = LoggerFactory.getLogger(MatchingService.class);

    private final Counter invocationCounter;
    private final Timer executionTimer;

    public MatchingService(MeterRegistry registry) {
        this.invocationCounter = Counter.builder("solver.invocations")
                .description("Number of times the StableMatch algorithm was invoked")
                .register(registry);

        this.executionTimer = Timer.builder("solver.execution.time")
                .description("Time taken to execute the StableMatch algorithm")
                .register(registry);
    }

    public MatchResponse solveGreedy(MatchRequest request) {
        logger.info("Starting GREEDY algorithm for {} students and {} courses.",
                request.getStudents().size(), request.getCourses().size());

        invocationCounter.increment();
        return executionTimer.record(() -> {
            try {
                List<MatchPair> matches = new ArrayList<>();
                List<String> unassigned = new ArrayList<>();
                Map<String, Integer> courseCapacities = new HashMap<>();

                for (CourseInput c : request.getCourses()) {
                    courseCapacities.put(c.getId(), c.getCapacity());
                }

                for (StudentInput student : request.getStudents()) {
                    boolean assigned = false;
                    if (student.getPreferences() != null) {
                        for (String coursePref : student.getPreferences()) {
                            int cap = courseCapacities.getOrDefault(coursePref, 0);
                            if (cap > 0) {
                                matches.add(new MatchPair(student.getId(), coursePref));
                                courseCapacities.put(coursePref, cap - 1);
                                assigned = true;
                                break;
                            }
                        }
                    }
                    if (!assigned) {
                        unassigned.add(student.getId());
                    }
                }

                logger.info("GREEDY finished successfully. Assigned: {}", matches.size());
                return new MatchResponse(matches, unassigned);

            } catch (Exception e) {
                logger.error("Error executing GREEDY algorithm: {}", e.getMessage(), e);
                throw e;
            }
        });
    }

    public MatchResponse solveRandom(MatchRequest request) {
        logger.info("Starting RANDOM algorithm for {} students.", request.getStudents().size());
        invocationCounter.increment();
        return executionTimer.record(() -> {
            try {
                List<MatchPair> matches = new ArrayList<>();
                List<String> unassigned = new ArrayList<>();

                List<StudentInput> shuffledStudents = new ArrayList<>(request.getStudents());
                Collections.shuffle(shuffledStudents);

                Map<String, Integer> courseCapacities = new HashMap<>();
                for (CourseInput c : request.getCourses()) {
                    courseCapacities.put(c.getId(), c.getCapacity());
                }

                for (StudentInput student : shuffledStudents) {
                    boolean assigned = false;
                    if (student.getPreferences() != null) {
                        for (String pref : student.getPreferences()) {
                            int cap = courseCapacities.getOrDefault(pref, 0);
                            if (cap > 0) {
                                matches.add(new MatchPair(student.getId(), pref));
                                courseCapacities.put(pref, cap - 1);
                                assigned = true;
                                break;
                            }
                        }
                    }
                    if (!assigned) {
                        unassigned.add(student.getId());
                    }
                }

                logger.info("RANDOM finished successfully. Unassigned students: {}", unassigned.size());
                return new MatchResponse(matches, unassigned);

            } catch (Exception e) {
                logger.error("Error executing RANDOM algorithm: {}", e.getMessage(), e);
                throw e;
            }
        });
    }
}