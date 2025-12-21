package org.example.stablematch.dto;

public class MatchPair {
    private String studentId;
    private String courseId;

    public MatchPair(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public String getStudentId() { return studentId; }
    public String getCourseId() { return courseId; }
}