package org.example.stablematch.service;

import org.example.stablematch.dto.*;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MatchingService {

    public MatchResponse solveGreedy(MatchRequest request) {
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
        return new MatchResponse(matches, unassigned);
    }

    public MatchResponse solveRandom(MatchRequest request) {
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

        return new MatchResponse(matches, unassigned);
    }
}