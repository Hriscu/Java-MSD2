package org.example.stablematch.dto;

import java.util.List;

public class MatchResponse {
    private List<MatchPair> assignments;
    private List<String> unassignedStudents;

    public MatchResponse(List<MatchPair> assignments, List<String> unassignedStudents) {
        this.assignments = assignments;
        this.unassignedStudents = unassignedStudents;
    }

    public List<MatchPair> getAssignments() { return assignments; }
    public List<String> getUnassignedStudents() { return unassignedStudents; }
}